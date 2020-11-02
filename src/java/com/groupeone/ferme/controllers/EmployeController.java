package com.groupeone.ferme.controllers;

import com.groupeone.ferme.listeners.OnAddFinish;
import com.groupeone.ferme.models.User;
import com.groupeone.ferme.utils.Res;
import com.groupeone.ferme.utils.Status;
import com.groupeone.ferme.utils.StatusConverter;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeController implements Initializable {
  private static User user;
  private static OnAddFinish listener;
  private static Stage stage;
  public TextField emailField;
  public PasswordField passwordField;
  public ComboBox<Status> statusComboBox;
  public Label errorMessage;
  public TextField nomField;
  public TextField salaireField;
  public TextField telephoneField;
  public Button annulerButton;
  public Button ajouterButton;
  public ImageView image;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    statusComboBox.getItems().addAll(
        Status.BOUTIQUIER,
        Status.GERANT_STOCK,
        Status.VETERINAIRE
    );
    image.setImage(new Image("/images/avatar.png", true));
    if (user != null) {
      emailField.setText(user.getEmail());
      passwordField.setText(user.getPassword());
      passwordField.setDisable(true);
      statusComboBox.setValue(user.getStatus());
      nomField.setText(user.getNom());
      telephoneField.setText(user.getTelephone());
      salaireField.setText("" + user.getSalaire());
      if (user.getImage() != null) {
        try {
          String url = new File(user.getImage()).toURI().toURL().toExternalForm();
          image.setImage(new Image(url, true));
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      }
    }
    statusComboBox.setConverter(new StatusConverter());
  }

  /**
   * Cette methode est appelée quant on vient sur la fenêtre de login pour la première fois
   *
   * @throws IOException Cette exception est déclenchée quand le chemin du fichier fxml ne peut pas être résolu
   */
  public static void show(User user, OnAddFinish listener) throws IOException {
    EmployeController.user = user;
    EmployeController.listener = listener;
    EmployeController.stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Ajouter nouvel employé");
    Parent root = Res.getFXML("Employe");
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void valider() {
    String nom = nomField.getText();
    String email = emailField.getText();
    String password = passwordField.getText();
    String salaire = salaireField.getText();
    String telephone = telephoneField.getText();
    Status status = statusComboBox.getValue();

    if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || salaire.isEmpty() || telephone.isEmpty() || status == null) {
      errorMessage.setVisible(true);
      errorMessage.setText("Tous champs doivent être remplis");
      return;
    }
    if (user != null) {
      user.setNom(nom)
          .setEmail(email)
          .setPassword(password)
          .setStatus(status)
          .setSalaire(Long.parseLong(salaire))
          .setTelephone(telephone)
          .update(listener::call);
    } else if (User.getUserByEmail(email) != null) {
      errorMessage.setVisible(true);
      errorMessage.setText("Erreur: Vous pouvez pas enregistrer deux personnes avec le meme mail");
    } else {
      new User().setNom(nom)
          .setEmail(email)
          .setPassword(password)
          .setStatus(status)
          .setSalaire(Long.parseLong(salaire))
          .setTelephone(telephone)
          .insert(listener::call);
    }
    stage.close();
  }

  public void annuler() {
    stage.close();
  }

  public void suppimer() {
    Alert alert = new Alert(
        Alert.AlertType.CONFIRMATION,
        String.format("Voulez vous vraiment %s dans la base de données", user.getNom())
    );
    alert.setHeaderText("Confirmez la suppression");
    Optional<ButtonType> optional = alert.showAndWait();
    if (!optional.isPresent() || optional.get() == ButtonType.CANCEL) return;
    user.delete(listener::call);
    stage.close();
  }
}
