package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Bovin;
import com.groupeone.ferme.models.Diagnostic;
import com.groupeone.ferme.utils.Res;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
  public VBox pageSante;
  public TableView<Diagnostic> tableView;
  public TableColumn<Diagnostic, Integer> id;
  public TableColumn<Diagnostic, String> maladie;
  public TableColumn<Diagnostic, String> personnel;
  public TableColumn<Diagnostic, String> description;
  public TableColumn<Diagnostic, String> animal;
  public TableColumn<Diagnostic, String> date;
  public Button supprimer;
  public Button ajouter;
  public Button valider;
  private Diagnostic selectedItem;
  private final Set<Diagnostic> editedDiagnostics = new HashSet<>();
  private final Set<Diagnostic> newDiagnostics = new HashSet<>();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    pageSante.setPrefHeight(App.screen.getHeight());
    reload();
    personnel.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPersonnel().getNom()));
    animal.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAnimal().getIdAnimal() + ""));
    date.setCellValueFactory(param -> new SimpleStringProperty(Res.getFormattedDate(param.getValue().getDate())));

    animal.setCellFactory(TextFieldTableCell.forTableColumn());
    description.setCellFactory(TextFieldTableCell.forTableColumn());
    maladie.setCellFactory(TextFieldTableCell.forTableColumn());
    tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedItem = newValue);
  }

  public void reload() {
    Diagnostic.getAll(result -> tableView.setItems(FXCollections.observableArrayList(result)));
  }

  public void supprimer() {
    if (selectedItem != null) {
      selectedItem.delete(() -> {
        selectedItem = null;
        reload();
      });
    }
  }

  public void ajouter() {
    Diagnostic diagnostic = new Diagnostic()
        .setDate(LocalDateTime.now())
        .setAnimal(new Bovin())
        .setPersonnel(AccueilController.user);
    tableView.getItems().add(diagnostic);
    newDiagnostics.add(diagnostic);
  }

  public void enregistrer() {
    Diagnostic.update(editedDiagnostics, () -> {
      editedDiagnostics.clear();
      reload();
    });
    Diagnostic.add(newDiagnostics, () -> {
      newDiagnostics.clear();
      reload();
    });
  }

  public void commitChanges(TableColumn.CellEditEvent<Diagnostic, String> editEvent) {
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
    if (diagnostic.getId() > 0) editedDiagnostics.add(diagnostic);
  }
}
