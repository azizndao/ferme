package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Stock;
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

public class StockController implements Initializable {

  public TableView<Stock> tableView;
  public TableColumn<Stock, Integer> numero;
  public TableColumn<Stock, String> type;
  public TableColumn<Stock, String> quantite;
  public TableColumn<Stock, String> date;
  public TableColumn<Stock, String> gerant;
  public VBox stockPage;
  public Button supprimer;
  public Button valider;
  public Button ajouter;
  private final Set<Stock> editingStocks = new HashSet<>();
  private final Set<Stock> newStocks = new HashSet<>();
  private Stock selectedStock;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initTable();
  }

  private void initTable() {
    stockPage.setPrefHeight(App.screen.getHeight());

    type.setCellFactory(TextFieldTableCell.forTableColumn());
    quantite.setCellFactory(TextFieldTableCell.forTableColumn());

    quantite.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getQuantite() + ""));
    gerant.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPersonnel().getNom()));
    date.setCellValueFactory(param -> new SimpleStringProperty(Res.getFormattedDate(param.getValue().getDate())));

    tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selectedStock = newValue);
    reload();
  }

  public void reload() {
    Stock.getAll((result) -> tableView.setItems(FXCollections.observableList(result)));
  }

  public void validerModifications() {
    Stock.add(newStocks, () -> {
      reload();
      newStocks.clear();
    });
    Stock.update(editingStocks, () -> {
      reload();
      editingStocks.clear();
    });
  }

  public void ajouter() {
    Stock stock = new Stock()
        .setPersonnel(AccueilController.user)
        .setDate(LocalDateTime.now());
    tableView.getItems().add(stock);
    newStocks.add(stock);
  }

  public void supprimer() {
    if (selectedStock != null)
      selectedStock.delete(() -> {
        reload();
        selectedStock = null;
      });
  }

  public void commitValue(TableColumn.CellEditEvent<Stock, String> editEvent) {
    Stock stock = editEvent.getRowValue();
    String id = editEvent.getTableColumn().getId();
    String value = editEvent.getNewValue();
    switch (id) {
      case "type":
        stock.setType(value);
        break;
      case "quantite":
        stock.setQuantite(Double.parseDouble(value));
        break;
    }
    if (stock.getId() != 0) editingStocks.add(stock);
  }
}
