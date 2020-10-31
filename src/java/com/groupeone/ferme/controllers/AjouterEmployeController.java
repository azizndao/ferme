package com.groupeone.ferme.controllers;

import com.groupeone.ferme.listeners.OnAddFinish;
import com.groupeone.ferme.models.User;
import com.groupeone.ferme.utils.*;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjouterEmployeController implements Initializable {
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


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    statusComboBox.getItems().addAll(
        Status.BOUTIQUIER,
        Status.GERANT_STOCK,
        Status.VETERINAIRE
    );
    if (user != null) {
      emailField.setText(user.getEmail());
      passwordField.setText(user.getPassword());
      passwordField.setDisable(true);
      statusComboBox.setValue(user.getStatus());
      nomField.setText(user.getNom());
      telephoneField.setText(user.getTelephone());
      salaireField.setText("" + user.getSalaire());
    }
    statusComboBox.setConverter(new StatusConverter());
  }

  /**
   * Cette methode est appelée quant on vient sur la fenêtre de login pour la première fois
   *
   * @throws IOException Cette exception est déclenchée quand le chemin du fichier fxml ne peut pas être résolu
   */
  public static void show(User user, OnAddFinish listener) throws IOException {
    AjouterEmployeController.user = user;
    AjouterEmployeController.listener = listener;
    AjouterEmployeController.stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Ajouter nouvel employé");
    Parent root = Res.getFXML("Ajouter");
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void ajouter() {
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
      modifier(nom, email, password, telephone, salaire, status);
      return;
    }
    new Thread(() -> {
      String selectQuery = "SELECT email FROM users WHERE email=?";
      try (PreparedStatement stm = Database.getInstance().prepareStatement(selectQuery)) {
        stm.setString(1, email);
        ResultSet resultSet = stm.executeQuery();
        if (resultSet.next()) {
          errorMessage.setVisible(true);
          errorMessage.setText("Erreur: Vous pouvez pas enregistrer deux personnes avec le meme mail");
          return;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

      String query = "INSERT INTO users (nom, email, mot_de_passe, telephone,salaire, status) VALUES (?,?,?,?,?,?)";
      prepareStatement(nom, email, password, salaire, telephone, status, query);
    }).start();
  }

  public void modifier(String nom, String email, String password, String telephone, String salaire, Status status) {
    new Thread(() -> {
      String query = "UPDATE users SET nom=?, email=?, mot_de_passe=?, telephone=?,salaire=?, status=? WHERE id=" + user.getId();
      prepareStatement(nom, email, password, salaire, telephone, status, query);
    }).start();
  }

  private void prepareStatement(String nom, String email, String password, String salaire, String telephone, Status status, String query) {
    try (PreparedStatement stm = Database.getInstance().prepareStatement(query)) {
      stm.setString(1, nom);
      stm.setString(2, email);
      stm.setString(3, password);
      stm.setString(4, telephone);
      stm.setString(5, salaire);
      stm.setString(6, status.label);
      stm.execute();
      Platform.runLater(() -> {
        listener.call();
        stage.close();
      });
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void annuler() {
    stage.close();
  }

  public void suppimer() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, String.format("Voulez vous vraiment %s dans la base de données", user.getNom()));
    alert.setHeaderText("Confirmez la suppression");
    Optional<ButtonType> optional = alert.showAndWait();
    if (!optional.isPresent() || optional.get() == ButtonType.CANCEL) return;
    new Thread(() -> {
      String deleteQuery = "DELETE FROM users WHERE id=?";
      try (PreparedStatement stm = Database.getInstance().prepareStatement(deleteQuery)) {
        stm.setInt(1, user.getId());
        stm.execute();
        Platform.runLater(() -> {
          listener.call();
          stage.close();
        });
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }).start();
  }
}
