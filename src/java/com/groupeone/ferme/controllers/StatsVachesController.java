package com.groupeone.ferme.controllers;

import com.groupeone.ferme.models.Animal;
import com.groupeone.ferme.models.Boeuf;
import com.groupeone.ferme.models.Vache;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
  public TableColumn<Animal, LocalDateTime> dateNaissance;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    List<Animal> animals = new ArrayList<>();
    animals.addAll(Vache.recupererVaches());
    animals.addAll(Boeuf.recupererVaches());
    animals.sort(Comparator.comparingInt(Animal::getIdAnimal));
    tableView.setItems(FXCollections.observableList(animals));
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
}
