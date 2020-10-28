package com.groupeone.ferme.controllers;

import com.groupeone.ferme.models.Personnel;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonneController implements Initializable {

  public VBox personne;
  public Label nom;
  public Label email;
  public Label telephone;
  public Label salaire;
  public ImageView imageView;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  public void setPersonnel(Personnel personnel) {
    nom.setText(personnel.getPassword() + " " + personnel.getNom());
    email.setText(personnel.getEmail());
    telephone.setText(personnel.getTelephone());
    salaire.setText(personnel.getSalaire() + " FCA/M");
    imageView.setImage(new Image(personnel.getImage()));
  }
}
