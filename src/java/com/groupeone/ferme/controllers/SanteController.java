package com.groupeone.ferme.controllers;

import com.groupeone.ferme.models.Diagnostic;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class SanteController implements Initializable {
  public TableColumn<Diagnostic, Integer> id;
  public TableColumn<Diagnostic, String> maladie;
  public TableColumn<Diagnostic, String> personnel;
  public TableColumn<Diagnostic, String> description;
  public TableView<Diagnostic> tableView;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    tableView.setItems(FXCollections.observableList(
        Diagnostic.recupererDiagnostics()
    ));
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
}
