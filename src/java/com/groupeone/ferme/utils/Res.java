package com.groupeone.ferme.utils;

import com.groupeone.ferme.controllers.AuthController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class Res {
  public static <T> T getNode(String name) throws IOException {
    return FXMLLoader.load(AuthController.class.getResource("/fxml/" + name + ".fxml"));
  }
}
