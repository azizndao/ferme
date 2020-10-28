package com.groupeone.ferme.customview;

import com.groupeone.ferme.controllers.PersonneController;
import com.groupeone.ferme.models.Personnel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Cette class represente une vue qui montre les information d'une personne dans la ferme
 */
public class PersonneView extends Pane {

  private PersonneController controller;
  private Node view;

  public PersonneView() {
    this(null);
  }

  public PersonneView(Personnel personnel) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Personne.fxml"));
    fxmlLoader.setControllerFactory(param -> controller = new PersonneController());
    try {
      view = fxmlLoader.load();
      getChildren().add(view);
      if (personnel != null) setPersonnel(personnel);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setPersonnel(Personnel personnel) {
    controller.setPersonnel(personnel);
  }
}
