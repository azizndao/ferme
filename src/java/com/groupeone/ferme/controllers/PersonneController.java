package com.groupeone.ferme.controllers;

import com.groupeone.ferme.listeners.OnAddFinish;
import com.groupeone.ferme.models.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonneController implements Initializable {

  public VBox personne;
  public Label nomField;
  public Label emailField;
  public Label telephoneField;
  public Label salaireField;
  public ImageView imageView;
  public Label statusComboBox;
  public Button modifier;
  private User personnel;
  private OnAddFinish listener;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void setPersonnel(User personnel, OnAddFinish listener) {
    this.personnel = personnel;
    this.listener = listener;
    nomField.setText(personnel.getNom());
    emailField.setText(personnel.getEmail());
    telephoneField.setText(personnel.getTelephone());
    salaireField.setText(personnel.getSalaire() + " FCA/M");
    statusComboBox.setText(personnel.getStatus().label);
    try {
      if (personnel.getImage() == null) {
        imageView.setImage(new Image("/images/avatar.png", true));
      }else {
        if (personnel.getImage().startsWith("/images/")){
          imageView.setImage(new Image(personnel.getImage(), true));
        }else {
          String url = new File(personnel.getImage()).toURI().toURL().toExternalForm();
          imageView.setImage(new Image(url, true));
        }
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  public void voirPlus() throws IOException {
    EmployeController.show(personnel,() -> listener.call());
  }
}
