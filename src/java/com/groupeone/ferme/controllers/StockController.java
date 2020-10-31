package com.groupeone.ferme.controllers;

import com.groupeone.ferme.models.Stock;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class StockController implements Initializable {

  public TableView<Stock> tableView;
  public TableColumn<Stock, Integer> numero;
  public TableColumn<Stock, String> type;
  public TableColumn<Stock, Double> quantite;
  public TableColumn<Stock, LocalDateTime> date;
  public TableColumn<Stock, String> gerant;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    changerStocks();
  }

  private void changerStocks() {
    new Thread(() ->
        Platform.runLater(() -> tableView.setItems(FXCollections.observableList(Stock.recupererStock())))
    ).start();
  }
}
