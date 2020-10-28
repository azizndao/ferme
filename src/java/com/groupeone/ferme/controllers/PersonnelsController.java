package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.customview.PersonneView;
import com.groupeone.ferme.models.Personnel;
import com.groupeone.ferme.utils.Res;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonnelsController implements Initializable {
  private static Stage stage;
  static Rectangle2D screen = App.screen;
  public FlowPane personnels;

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
  }

  Node[] personnelItems = {
      new PersonneView(
          new Personnel(
              1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000"
          )
      ),
      new PersonneView(
          new Personnel(
              1, "Abdou Aziz", "Ndao", "/images/personnel-2.jpg", "abdouaziz@ndao.bk", "aziz", "77 300 99 38", "90 000"
          )
      ),
      new PersonneView(
          new Personnel(
              1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000"
          )
      ),
      new PersonneView(
          new Personnel(
              1, "Abdou Aziz", "Ndao", "/images/personnel-2.jpg", "abdouaziz@ndao.bk", "aziz", "77 300 99 38", "90 000"
          )
      ),
      new PersonneView(
          new Personnel(
              1, "Youssonpha", "Sane", "/images/personnel-3.jpg", "youssou@sane.th", "youssou", "70 456 34 65", "105 000"
          )
      ),
      new PersonneView(
          new Personnel(
              1, "Aly", "Ka", "/images/personnel-1.png", "aky@ka.th", "aly", "78 092 02 38", "100 000"
          )
      ),
      new PersonneView(
          new Personnel(
              1, "Abdou Aziz", "Ndao", "/images/personnel-2.jpg", "abdouaziz@ndao.bk", "aziz", "77 300 99 38", "90 000"
          )
      ),
      new PersonneView(
          new Personnel(
              1, "Youssonpha", "Sane", "/images/personnel-3.jpg", "youssou@sane.th", "youssou", "70 456 34 65", "105 000"
          )
      )
  };

}
