package com.groupeone.ferme;

import com.groupeone.ferme.controllers.AuthController;
import com.groupeone.ferme.utils.Database;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

  public static Rectangle2D screen = Screen.getPrimary().getBounds();

  @Override
  public void init() throws Exception {
    Database.connect("jdbc:mariadb://localhost/ferme", "abdou", "aziz");
    super.init();
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    AuthController.showIn(primaryStage);
  }

  @Override
  public void stop() throws Exception {
    Database.close();
    super.stop();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
