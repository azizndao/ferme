
package com.groupeone.ferme.controllers;

import com.groupeone.ferme.models.Proprietaire;
import com.groupeone.ferme.utils.Database;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

  public TextField email;
  public TextField password;
  public ComboBox<Status> status;
  private static Stage stage;
  public Label errorMessage;
  public Button submitButton;

  public static void show() {
    stage.show();
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Status[] statusItems = {Status.PROPRIETAIRE, Status.BOUTIQUIER, Status.GERANT_STOCK, Status.VETERINAIRE};
    status.getItems().addAll(statusItems);
    status.setConverter(STATUS_STRING_CONVERTER);
    status.setValue(statusItems[0]);
    email.setText("azizndao71@gmail.com");
    password.setText("aziz");
  }

  public static final StringConverter<Status> STATUS_STRING_CONVERTER = new StringConverter<Status>() {
    @Override
    public String toString(Status status) {
      if (status == null) return null;
      return status.label;
    }

    @Override
    public Status fromString(String label) {
      return Status.valueOf(label);
    }
  };

  public static void showIn(Stage stage) throws IOException {
    AuthController.stage = stage;
    AuthController.stage.setTitle("Ferme");
    Parent root = FXMLLoader.load(AuthController.class.getResource("/fxml/AuthWindow.fxml"));
    Scene scene = new Scene(root);
    AuthController.stage.setScene(scene);
    AuthController.stage.show();
  }


  public void cancelLogin() {
    Platform.exit();
  }

  public void submitLogin() {
    String emailValue = email.getText();
    String passwordValue = password.getText();

    if (emailValue.isEmpty() || passwordValue.isEmpty()) {
      errorMessage.setText("Erreur: Tous les champs sont requisent");
      errorMessage.setVisible(true);
      return;
    }

    Database db = Database.getInstance();
    String statusValue = status.getValue().table;

    String query = String.format("SELECT * FROM %s WHERE email=? AND password=?", statusValue);
    try (PreparedStatement stm = db.preparedStatement(query)) {
      stm.setString(1, emailValue);
      stm.setString(2, passwordValue);
      ResultSet result = stm.executeQuery();
      if (result.next()) {
        Proprietaire proprietaire = new Proprietaire();
        proprietaire.setId(result.getInt(1));
        proprietaire.setPrenom(result.getString(2));
        proprietaire.setNom(result.getString(3));
        proprietaire.setEmail(result.getString(4));
        proprietaire.setPassword(result.getString(5));
        ProprietaireController.show(proprietaire);
        stage.close();
      } else {
        email.setText("");
        password.setText("");
        errorMessage.setVisible(true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  enum Status {
    PROPRIETAIRE("Proprietaire", "proprietaire"),
    GERANT_STOCK("Gerant Stock", "personnels"),
    BOUTIQUIER("Boutiqueier", "personnels"),
    VETERINAIRE("Veterinaire", "personnels");

    private final String label;
    private final String table;

    Status(String label, String table) {
      this.label = label;
      this.table = table;
    }
  }
}
