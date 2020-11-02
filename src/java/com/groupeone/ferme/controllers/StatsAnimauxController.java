package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Bovin;
import com.groupeone.ferme.utils.Res;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class StatsAnimauxController implements Initializable {

  public TableView<Bovin> tableView;
  public TableColumn<Bovin, Integer> id;
  public TableColumn<Bovin, Bovin.Sexe> sexe;
  public TableColumn<Bovin, String> vivant;
  public TableColumn<Bovin, String> dateNaissance;
  public VBox pageStats;
  public Button supprimer;
  public Button ajouter;
  public Button valider;
  public TableColumn<Bovin, Bovin.Etat> etat;
  private final Set<Bovin> editedBovins = new HashSet<>();
  private final Set<Bovin> newBovin = new HashSet<>();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    pageStats.setPrefHeight(App.screen.getHeight());
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    sexe.setCellFactory(ComboBoxTableCell.forTableColumn(Bovin.Sexe.values()));
    etat.setCellFactory(ComboBoxTableCell.forTableColumn(Bovin.Etat.values()));
    vivant.setCellFactory(ChoiceBoxTableCell.forTableColumn("oui", "non"));
    dateNaissance.setCellFactory(TextFieldTableCell.forTableColumn());
    vivant.setCellValueFactory(param -> {
      boolean vivant = param.getValue().isVivant();
      if (vivant) return new SimpleStringProperty("oui");
      return new SimpleStringProperty("non");
    });
    dateNaissance.setCellValueFactory(param -> {
      LocalDateTime naissance = param.getValue().getDateNaissance();
      return new SimpleStringProperty(Res.getFormattedDate(naissance));
    });
    reload();
  }

  private void reload() {
    Bovin.getAll(result -> {
      result.sort(Comparator.comparingInt(Bovin::getIdAnimal));
      tableView.setItems(FXCollections.observableArrayList(result));
    });
  }

  public void supprimer() {
    Bovin selectedBovin = tableView.getSelectionModel().selectedItemProperty().getValue();
    if (selectedBovin != null) {
      selectedBovin.delete(this::reload);
    }
  }

  public void enregistrer() {
    Bovin.enregistrer(newBovin, () -> {
      newBovin.clear();
      reload();
    });
    Bovin.update(editedBovins, () -> {
      editedBovins.clear();
      reload();
    });
  }

  public void ajouter() {
    Bovin bovin = new Bovin()
        .setDateNaissance(LocalDateTime.now())
        .setSexe(Bovin.Sexe.FEMELLE)
        .setEtat(Bovin.Etat.NULL);
    newBovin.add(bovin);
    tableView.getItems().add(bovin);
  }

  public void commitChange(TableColumn.CellEditEvent<Bovin, String> editEvent) {
    editEvent.getRowValue().setVivant(editEvent.getNewValue().equals("oui"));
  }

  public void commitSexeValue(TableColumn.CellEditEvent<Bovin, Bovin.Sexe> editEvent) {
    editEvent.getRowValue().setSexe(editEvent.getNewValue());
  }

  public void commitEtatValue(TableColumn.CellEditEvent<Bovin, Bovin.Etat> editEvent) {
    editEvent.getRowValue().setEtat(editEvent.getNewValue());
  }

  public void showDatePiker(TableColumn.CellEditEvent<Bovin, String> editEvent) {
    System.out.println("Dtaa ");
    new DatePicker().show();
  }
}
