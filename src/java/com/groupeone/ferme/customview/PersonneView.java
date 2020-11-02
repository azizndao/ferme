package com.groupeone.ferme.customview;

import com.groupeone.ferme.controllers.PersonneController;
import com.groupeone.ferme.listeners.OnAddFinish;
import com.groupeone.ferme.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Cette class représente une vue qui montre les information d'une personne dans la ferme
 */
public class PersonneView extends Pane {

  private PersonneController controller;
  private final OnAddFinish listener;

  public PersonneView() {
    this(null, null);
  }

  public PersonneView(User personnel, OnAddFinish listener) {
    this.listener = listener;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Personne.fxml"));
    fxmlLoader.setControllerFactory(param -> controller = new PersonneController());
    try {
      Node view = fxmlLoader.load();
      getChildren().add(view);
      if (personnel != null) setPersonnel(personnel);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setPersonnel(User personnel) {
    controller.setPersonnel(personnel, listener);
  }
}
