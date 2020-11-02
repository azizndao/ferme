package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Animal;
import com.groupeone.ferme.models.Boeuf;
import com.groupeone.ferme.models.Vache;
import com.groupeone.ferme.utils.Res;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class StatsVachesController implements Initializable {

  public TableView<Animal> tableView;
  public TableColumn<Animal, Integer> id;
  public TableColumn<Animal, String> sexe;
  public TableColumn<Animal, String> vivant;
  public TableColumn<Animal, String> dateNaissance;
  public VBox pageStats;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    pageStats.setPrefHeight(App.screen.getHeight());
    List<Animal> animals = new ArrayList<>();
    animals.addAll(Vache.recupererVaches());
    animals.addAll(Boeuf.recupererVaches());
    animals.sort(Comparator.comparingInt(Animal::getIdAnimal));
    tableView.setItems(FXCollections.observableList(animals));
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    sexe.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSexe().label));
    vivant.setCellValueFactory(param -> {
      boolean vivant = param.getValue().isVivant();
      if (vivant) return new SimpleStringProperty("oui");
      return new SimpleStringProperty("non");
    });
    dateNaissance.setCellValueFactory(param -> {
      LocalDateTime naissance = param.getValue().getDateNaissance();
      return new SimpleStringProperty(
          Res.getFormattedDate(naissance)
      );
    });
  }
}
