package com.groupeone.ferme;

import com.groupeone.ferme.controllers.AuthController;
import com.groupeone.ferme.models.UserDao;
import com.groupeone.ferme.utils.Database;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import me.abdou.reflector.DaoStore;

import java.io.IOException;
import java.net.UnknownServiceException;

/**
 * La classe qui représente notre application
 */
public class App extends Application {

  /**
   * cette variable va nous permettre de récupérer les dimension de l'écran dans laquelle
   * la fenêtre s'est  afficher
   */
  public static Rectangle2D screen = Screen.getPrimary().getBounds();

  /**
   * Ici on initialise la connection la base de données
   *
   * @throws Exception quant la connection à la base n'a pas réussi
   */
  @Override
  public void init() throws Exception {
    Database.connect("jdbc:mysql://localhost/ferme", "abdou", "aziz");
    super.init();
  }

  /**
   * On Affiche la fenêtre de login qui est le point de notre application
   *
   * @param primaryStage le stage qui nous donne le système et que l'on utilise
   *                     pour afficher notre première fenêtre
   * @throws IOException lors qu'il y'a erreur
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    AuthController.create(primaryStage);
  }

  /**
   * Quant application s'est terminée on ferme la connection la base de données
   * pour évité les fuites de memoire
   *
   * @throws Exception quant il y'a erreur
   */
  @Override
  public void stop() throws Exception {
    Database.close();
    super.stop();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
