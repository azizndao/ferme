package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Proprietaire;
import com.groupeone.ferme.utils.Res;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProprietaireController implements Initializable {

  public VBox sidebar;
  public static HBox root;
  public Label appName;
  final List<Button> destinations = new ArrayList<>();
  private static Proprietaire proprietaire;
  static Rectangle2D screen = App.screen;
  public ImageView userImage;
  public Label userEmail;
  public Label userName;
  public Button signOutButton;
  public Button showAccountButton;
  private static Stage stage;
  public VBox content;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    sidebar.setPrefWidth(screen.getWidth() * 0.25);
    userName.setText(proprietaire.getPrenom() + " " + proprietaire.getNom());
    userEmail.setText(proprietaire.getEmail());
    for (Node child : sidebar.getChildren()) {
      if (child instanceof Button) destinations.add((Button) child);
    }

    content.setPrefWidth(screen.getWidth() * 0.75);
    content.setPrefHeight(screen.getHeight());
    listenerDestinationChange();
  }

  private void listenerDestinationChange() {
    destinations.forEach(button -> button.setOnAction(event -> {
      destinations.forEach(btn -> btn.getStyleClass().remove("active"));
      button.getStyleClass().add("active");
      try {
        Node currentPage;
        switch (button.getText()) {
          case "Stock":
            currentPage = Res.getNode("Stock");
            break;
          case "Sante":
            currentPage = Res.getNode("Sante");
            break;
          case "Transaction":
            currentPage = Res.getNode("Transactions");
            break;
          case "Finance":
            currentPage = Res.getNode("Finances");
            break;
          case "Stats. vaches":
            currentPage = Res.getNode("StatsVaches");
            break;
          default:
            currentPage = Res.getNode("Personnels");
            break;
        }
        content.getChildren().clear();
        content.getChildren().add(currentPage);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }));
  }

  public static void show(Proprietaire proprietaire) throws IOException {
    ProprietaireController.proprietaire = proprietaire;
    stage = new Stage();
    stage.setMaximized(true);
    root = Res.getNode("Proprietaire");
    Scene scene = new Scene(root, screen.getWidth() * 0.8, screen.getHeight() * 0.9);
    stage.setScene(scene);
    stage.show();
  }

  public void showAccount() {

  }

  public void signOut() {
    AuthController.show();
    stage.close();
  }
}
