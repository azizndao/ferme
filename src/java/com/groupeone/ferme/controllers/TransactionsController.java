package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Transaction;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
  public VBox pageTransaction;
  public TableView<Transaction> tableView;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    pageTransaction.setPrefHeight(App.screen.getHeight());
  }
}
