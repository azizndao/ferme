package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.customview.PersonneView;
import com.groupeone.ferme.models.User;
import com.groupeone.ferme.utils.Database;
import com.groupeone.ferme.utils.Status;
import com.groupeone.ferme.utils.StatusConverter;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Le controller pour la partie qui affichera les employés de la ferme
 */
public class PersonnelsController implements Initializable {
  private static Stage stage;
  static Rectangle2D screen = App.screen;
  /*  public TableView<User> tableView;
    public TableColumn<User, Integer> id;
    public TableColumn<User, ImageView> avatar;
    public TableColumn<User, String> nom;
    public TableColumn<User, String> email;
    public TableColumn<User, String> telephone;
    public TableColumn<User, String> status;*/
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

  private void changerPersonnels(Status newValue) {
    new Thread(() -> {
      try (Statement statement = Database.getInstance().createStatement()) {
        ResultSet result;
        if (newValue == null || newValue == Status.ALL) {
          result = statement.executeQuery("SELECT * FROM users WHERE status != 'Propriétaire'");
        } else {
          String sql = String.format("SELECT * FROM users WHERE status != 'Propriétaire' AND status='%s'", newValue.label);
          result = statement.executeQuery(sql);
        }
        final List<PersonneView> users = new ArrayList<>();
        while (result.next()) {
          User user = User.fromResultSet(result);
          users.add(new PersonneView(user, () -> changerPersonnels(null)));
        }
        Platform.runLater(() -> {
          this.flowPane.getChildren().clear();
          this.flowPane.getChildren().addAll(users);
        });
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }).start();
  }
  public void ajouterEmployer() throws IOException {
    AjouterEmployeController.show(null, () -> changerPersonnels(null));
  }
}
