package com.groupeone.ferme.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Cette classe va nous aidée a éviter dans le code
 */
public final class Res {
  public static <T extends Parent> T getFXML(String name) throws IOException {
    T parent = FXMLLoader.load(Res.class.getResource("/fxml/" + name + ".fxml"));
    parent.getStylesheets().add(getCss("Default"));
    return parent;
  }

  public static String getCss(String name) {
    return Res.class.getResource("/css/" + name + ".css").toExternalForm();
  }

  public static String getFormattedDate(LocalDateTime dateTime) {
    return String.format(
        "%s/%s/%s à %d:%d",
        dateTime.getDayOfMonth(),
        dateTime.getMonth().getValue(),
        dateTime.getYear(),
        dateTime.getHour(),
        dateTime.getMinute()
    );
  }
}
