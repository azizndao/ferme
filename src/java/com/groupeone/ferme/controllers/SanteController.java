package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Diagnostic;
import com.groupeone.ferme.utils.Res;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class SanteController implements Initializable {
  public TableColumn<Diagnostic, Integer> id;
  public TableColumn<Diagnostic, String> maladie;
  public TableColumn<Diagnostic, String> personnel;
  public TableColumn<Diagnostic, String> description;
  public TableView<Diagnostic> tableView;
  public VBox pageSante;
  public TableColumn<Diagnostic, String> animal;
  public TableColumn<Diagnostic, String> date;
  public Button supprimer;
  public Button ajouter;
  public Button valider;
  private ObservableList<Diagnostic> diagnostics;
  private final Set<Diagnostic> editingDiagnostics = new HashSet<>();
  private final Set<Diagnostic> addingDiagnostics = new HashSet<>();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Diagnostic.getAll(result -> diagnostics = FXCollections.observableArrayList(result));
    pageSante.setPrefHeight(App.screen.getHeight());

    personnel.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPersonnel().getNom()));
    animal.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnimal().getIdAnimal() + ""));
    date.setCellValueFactory(param -> new SimpleStringProperty(Res.getFormattedDate(param.getValue().getDate())));

    animal.setCellFactory(TextFieldTableCell.forTableColumn());
    description.setCellFactory(TextFieldTableCell.forTableColumn());
    maladie.setCellFactory(TextFieldTableCell.forTableColumn());
    tableView.setItems(diagnostics);
  }

  public void supprimer() {

  }

  public void ajouter() {
    Diagnostic diagnostic = new Diagnostic()
        .setDate(LocalDateTime.now())
        .setPersonnel(AccueilController.user);
    addingDiagnostics.add(diagnostic);
  }

  public void enregistrer() {
      Diagnostic.update(editingDiagnostics, () -> {});
      Diagnostic.add(addingDiagnostics);
  }

  public void valider(TableColumn.CellEditEvent<Diagnostic, String> editEvent) {
    Diagnostic diagnostic = editEvent.getRowValue();
    String newValue = editEvent.getNewValue();
    String id = editEvent.getTableColumn().getId();
    switch (id) {
      case "animal":
        diagnostic.getAnimal().setIdAnimal(Integer.parseInt(newValue));
        break;
      case "maladie":
        diagnostic.setMaladie(newValue);
        break;
      case "description":
        diagnostic.setDescription(newValue);
        break;
    }

    if (diagnostic.getId() > 0) editingDiagnostics.add(diagnostic);
  }
}
