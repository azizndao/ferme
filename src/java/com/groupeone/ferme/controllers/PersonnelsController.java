package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Personnel;
import com.groupeone.ferme.utils.Res;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonnelsController implements Initializable {
  private static Stage stage;
  static Rectangle2D screen = App.screen;
  public TableView<Personnel> personnels;
  public TableColumn<Personnel, Integer> id;
  public TableColumn<Personnel, String> avatar;
  public TableColumn<Personnel, String> prenom;
  public TableColumn<Personnel, String> nom;
  public TableColumn<Personnel, String> email;
  public TableColumn<Personnel, String> telephone;
  public TableColumn<Personnel, String> status;

  public static void show() throws IOException {
    stage = new Stage();
    stage.setMaximized(true);
    Parent root = Res.getNode("Personnels");
    Scene scene = new Scene(root, screen.getWidth(), screen.getHeight());
    stage.setScene(scene);
    stage.show();
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
//    personnels.getChildren().addAll(personnelItems);
    personnels.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    initTableView();
  }

  private void initTableView() {
    id.setCellValueFactory(new PropertyValueFactory<>("id"));
    avatar.setCellValueFactory(new PropertyValueFactory<>("avatar"));
    prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
    nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
    email.setCellValueFactory(new PropertyValueFactory<>("email"));
    telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
    status.setCellValueFactory(new PropertyValueFactory<>("status"));
    personnels.getItems().addAll(personnelItems);
  }

  Personnel[] personnelItems = {
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
      new Personnel(
          1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000", "Hello World", ""
      ),
  };

}
