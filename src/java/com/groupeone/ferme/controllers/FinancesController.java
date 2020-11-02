package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FinancesController implements Initializable {
  public VBox pageFinances;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    pageFinances.setPrefHeight(App.screen.getHeight());
  }
}
