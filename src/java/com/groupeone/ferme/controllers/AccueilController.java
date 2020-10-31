package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.User;
import com.groupeone.ferme.utils.Res;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Le controller pour la fenêtre du propriétaire
 */
public class AccueilController implements Initializable {

  public VBox sidebar;
  public static HBox root;
  public Label appName;
  final List<Button> destinations = new ArrayList<>();
  private static User user;
  static Rectangle2D screen = App.screen;
  public ImageView userImage;
  public Label userEmail;
  public Label userName;
  public Button signOutButton;
  private static Stage stage;
  public VBox content;
  public Label statusComboBox;

  /**
   * Dans cette methode on initialise les valeurs par default et on écoute les évènement
   * du click (onAction) sur les bouton de navigation
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    setUserInformations();
    getNavigationButtons();
    signOutButton.setDisable(false);
    content.setPrefWidth(screen.getWidth() * 0.75);
    content.setPrefHeight(screen.getHeight());
    listenerDestinationChange();
  }

  private void getNavigationButtons() {
    for (Node child : sidebar.getChildren()) {
      if (child instanceof Button) {
        destinations.add((Button) child);
        String userAccess = user.getStatus().access;
        Button button = (Button) child;
        if (!userAccess.equals("*")) {
          if (button.getText().equals(userAccess)) {
            button.getStyleClass().add("active");
          } else {
            button.setDisable(true);
            button.getStyleClass().remove("active");
            content.getChildren().clear();
            try {
              content.getChildren().add(Res.getFXML(userAccess));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }

  private void setUserInformations() {
    sidebar.setPrefWidth(screen.getWidth() * 0.25);
    userEmail.setText(user.getEmail());
    userName.setText(user.getNom());
    statusComboBox.setText(user.getStatus().label);
    try {
      String url = (new File(user.getImage())).toURI().toURL().toExternalForm();
      userImage.setImage(new Image(url, true));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Ici on écoute chacun des bouton  dans le sidebar enfin de détecter le click
   * et afficher le contenu demandé
   */
  private void listenerDestinationChange() {
    for (Button button : destinations) {
      button.setOnAction(event -> {
        destinations.forEach(btn -> btn.getStyleClass().remove("active"));
        button.getStyleClass().add("active");
        try {
          Node currentPage;
          switch (button.getText()) {
            case "Stock":
              currentPage = Res.getFXML("Stock");
              break;
            case "Sante":
              currentPage = Res.getFXML("Sante");
              break;
            case "Transactions":
              currentPage = Res.getFXML("Transactions");
              break;
            case "Finance":
              currentPage = Res.getFXML("Finances");
              break;
            case "Stats. vaches":
              currentPage = Res.getFXML("StatsVaches");
              break;
            default:
              currentPage = Res.getFXML("Personnels");
              break;
          }
          content.getChildren().clear();
          content.getChildren().add(currentPage);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }
  }

  /**
   * Cette methode est appelé quant c'est le propriétaire qui s'est connecté
   *
   * @param user les information de la propriétaire
   * @throws IOException quant le fichier fxml Propriétaire est introuvable
   */
  public static void show(User user) throws IOException {
    AccueilController.user = user;
    stage = new Stage();
    stage.setMaximized(true);
    root = Res.getFXML("Accueil");
    Scene scene = new Scene(root, screen.getWidth() * 0.8, screen.getHeight() * 0.9);
    stage.setScene(scene);
    stage.show();
  }

  public void showAccount() {

  }

  /**
   * Cette methode est appelé quant on click sur le bouton déconnecter
   * Elle déconnectera l'utilisateur courant et le redirige ensuite vers la fenêtre de
   * login
   */
  public void signOut() {
    AuthController.show();
    stage.close();
  }
}
