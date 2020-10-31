
package com.groupeone.ferme.controllers;

import com.groupeone.ferme.models.User;
import com.groupeone.ferme.utils.Database;
import com.groupeone.ferme.utils.Res;
import com.groupeone.ferme.utils.Status;
import com.groupeone.ferme.utils.StatusConverter;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

/**
 * Ce controller va géré authentification sur l'application
 */
public class AuthController implements Initializable {

  public TextField email;
  public TextField password;
  public ComboBox<Status> status;
  public Label errorMessage;
  public Button submitButton;
  private static Stage stage;

  /**
   * Cette methode est appelée quant la fenêtre d'authentification est fermée
   * et qu'on veuille la ouvrir de nouveau. En appelant cette methode, on évite de
   * de récréer la fenêtre mais on utilise la fenêtre deja créée
   */
  public static void show() {
    stage.show();
  }

  /**
   * Dans cette on initialize les données que l'on a besoin dans la fenêtre d'authentification
   */
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    Status[] statusItems = {Status.PROPRIETAIRE, Status.BOUTIQUIER, Status.GERANT_STOCK, Status.VETERINAIRE};
    status.getItems().addAll(statusItems);
    status.setConverter(new StatusConverter());
    email.setText("azizndao71@gmail.com");
    password.setText("aziz");
    status.setValue(statusItems[0]);
  }


  /**
   * Cette methode est appelée quant on vient sur la fenêtre de login pour la première fois
   *
   * @param stage Le stage sur lequel on affiche notre fenêtre de login. En suite il sera enregistré
   *              pour la prochaine fois qu'on ouvre cette fenêtre
   * @throws IOException Cette exception est déclenchée quant le chemin du fichier fxml ne peut pas être résolu
   */
  public static void create(Stage stage) throws IOException {
    AuthController.stage = stage;
    AuthController.stage.setTitle("Ferme");
    Parent root = Res.getFXML("AuthWindow");
    Scene scene = new Scene(root);
    AuthController.stage.setScene(scene);
    AuthController.stage.show();
  }

  /**
   * Cette methode sera appelée quant on appuie sur le button annuler dans la fenêtre de login
   * Elle se chargera de fermer la fenêtre et quitter l'application
   */
  public void cancelLogin() {
    Platform.exit();
  }

  /**
   * Cette methode sera appelée quant on appuie sur le bouton valider pour valider les données de
   * l'authentification. Elle se chargera de la verification en nous retournant une message erreur
   * en cas de problème sinon il nous montre la page d'accueil de l'utilisateur qui s'est connecté
   */
  public void submitLogin() {
    String emailValue = email.getText();
    String passwordValue = password.getText();

    if (emailValue.isEmpty() || passwordValue.isEmpty()) {
      errorMessage.setText("Erreur: Tous les champs sont requissent");
      errorMessage.setVisible(true);
      return;
    }
    errorMessage.setVisible(false);
    verifyLogin(emailValue, passwordValue);
  }

  /**
   * Cette methode nous permet de verifier l'authentification en allant verifier dans la base de données
   * si l'utilisateur y figure
   *
   * @param email    Le mail que l'utilisateur que l'utilisateur a saisi
   * @param password Le mot de passe que l'utilisateur a saisi'
   */
  private void verifyLogin(String email, String password) {
    new Thread(() -> {
      Database db = Database.getInstance();
      String query = "SELECT * FROM users WHERE email=? AND mot_de_passe=? AND status=?";
      try (PreparedStatement stm = db.prepareStatement(query)) {
        stm.setString(1, email);
        stm.setString(2, password);
        stm.setString(3, status.getValue().label);
        ResultSet result = stm.executeQuery();
        if (result.next()) {
          User user = User.fromResultSet(result);
          Platform.runLater(() -> {
            try {
              if (user.getStatus() != Status.PROPRIETAIRE && user.getPassword().equals("1234")) {
                try {
                  ChangePasswordController.show(user);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              } else {
                AccueilController.show(user);
              }
              this.email.setText("");
              this.password.setText("");
              stage.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
        } else {
          Platform.runLater(() -> {
            this.email.setText("");
            this.password.setText("");
            errorMessage.setVisible(true);
          });
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }
}
