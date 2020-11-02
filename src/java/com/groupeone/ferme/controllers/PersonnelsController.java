package com.groupeone.ferme.controllers;

import com.groupeone.ferme.customview.PersonneView;
import com.groupeone.ferme.models.User;
import com.groupeone.ferme.utils.Status;
import com.groupeone.ferme.utils.StatusConverter;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Le controller pour la partie qui affichera les employés de la ferme
 */
public class PersonnelsController implements Initializable {
  public FlowPane flowPane;
  public ScrollPane scrollPane;
  public ComboBox<Status> filter;
  public Button ajouterEmploye;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    filter.getItems().addAll(
        Status.BOUTIQUIER,
        Status.GERANT_STOCK,
        Status.VETERINAIRE,
        Status.ALL
    );
    filter.setValue(Status.ALL);
    filter.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> changerPersonnels(newValue));
    filter.setConverter(new StatusConverter());
    changerPersonnels(null);
  }

  private void changerPersonnels(Status filter) {
    new Thread(() -> {
      List<User> users;
      if (filter == null || filter == Status.ALL) {
        users = User.getEmployees();
      } else {
        users = User.getEmployeesWithStatus(filter);
      }
      List<PersonneView> personneView = users.stream()
          .map(user -> new PersonneView(user, () -> changerPersonnels(null)))
          .collect(Collectors.toList());
      Platform.runLater(() -> {
        this.flowPane.getChildren().clear();
        this.flowPane.getChildren().addAll(personneView);
      });
    }).start();
  }

  public void ajouterEmployer() throws IOException {
    EmployeController.show(null, () -> changerPersonnels(null));
  }
}
