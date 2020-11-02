package com.groupeone.ferme.controllers;

import com.groupeone.ferme.models.User;
import com.groupeone.ferme.utils.Res;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangePasswordController {

  private static User user;
  private static Stage stage;
  public PasswordField password;
  public Label errorMessage;
  public Button submitButton;
  public PasswordField confirmerPassword;

  public static void show(User user) throws IOException {
    ChangePasswordController.user = user;
    stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setTitle("Ajouter nouvel employé");
    Parent root = Res.getFXML("ChangePassword");
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void confirmer() {
    String passwordText = password.getText();
    String confirmerPasswordText = confirmerPassword.getText();
    if (passwordText.isEmpty() || confirmerPasswordText.isEmpty()) {
      errorMessage.setVisible(true);
      errorMessage.setText("Erreur: Vous devez obligatoirement entrer un mot de passe");
    } else if (!passwordText.equals(confirmerPasswordText)) {
      errorMessage.setVisible(true);
      errorMessage.setText("Erreur: Les deux mots de passe sont différents");
    } else if (passwordText.equals("1234")) {
      errorMessage.setVisible(true);
      errorMessage.setText("Erreur: Vous ne pouvez conserver le même mot de passe");
    } else {
      errorMessage.setVisible(false);
      user.setPassword(passwordText).update(() -> {
        try {
          stage.close();
          AccueilController.show(user);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }
  }
}
