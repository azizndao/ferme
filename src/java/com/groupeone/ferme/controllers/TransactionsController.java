package com.groupeone.ferme.controllers;

import com.groupeone.ferme.App;
import com.groupeone.ferme.models.Transaction;
import com.groupeone.ferme.utils.Res;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TransactionsController implements Initializable {
  public VBox pageTransaction;
  public TableView<Transaction> tableView;
  public TableColumn<Transaction, String> produit;
  public TableColumn<Transaction, String> somme;
  public TableColumn<Transaction, String> date;
  public TableColumn<Transaction, Transaction.Type> type;
  public TableColumn<Transaction, Integer> id;
  public Button btnValider;
  public Button btnAjouter;
  public Button btnSupprimer;
  private final Set<Transaction> editedTransactions = new HashSet<>();
  private final Set<Transaction> newTransactions = new HashSet<>();
  public TableColumn<Transaction, String> personnel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    pageTransaction.setPrefHeight(App.screen.getHeight());
    produit.setCellFactory(TextFieldTableCell.forTableColumn());

    somme.setCellFactory(TextFieldTableCell.forTableColumn());
    somme.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSomme() + ""));

    type.setCellFactory(ComboBoxTableCell.forTableColumn(Transaction.Type.values()));
    date.setCellValueFactory(param -> new SimpleStringProperty(Res.getFormattedDate(param.getValue().getDate())));

    personnel.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPersonnel().getNom()));
    reload();
  }

  private void reload() {
    Transaction.getAll(result -> tableView.setItems(FXCollections.observableArrayList(result)));
  }

  public void supprimer() {
    Transaction transaction = tableView.getSelectionModel().selectedItemProperty().getValue();
    if (transaction != null) {
      transaction.delete(this::reload);
    }
  }

  public void enregistrer() {
    Transaction.enregistrer(newTransactions, () -> {
      newTransactions.clear();
      reload();
    });
    Transaction.update(editedTransactions, () -> {
      editedTransactions.clear();
      reload();
    });
  }

  public void ajouter() {
    Transaction transaction = new Transaction()
        .setDate(LocalDateTime.now())
        .setPersonnel(AccueilController.user);
    newTransactions.add(transaction);
    tableView.getItems().add(transaction);
  }

  public void commitTypeValue(TableColumn.CellEditEvent<Transaction, Transaction.Type> editEvent) {
    Transaction transaction = editEvent.getRowValue();
    editEvent.getRowValue().setType(editEvent.getNewValue());
    if (transaction.getId() > 0) editedTransactions.add(transaction);
  }

  public void commitSommeValue(TableColumn.CellEditEvent<Transaction, String> editEvent) {
    Transaction transaction = editEvent.getRowValue();
    double somme = Double.parseDouble(editEvent.getNewValue());
    transaction.setSomme(somme);
    if (transaction.getId() > 0) editedTransactions.add(transaction);
  }

  public void commitProduit(TableColumn.CellEditEvent<Transaction, String> editEvent) {
    Transaction transaction = editEvent.getRowValue();
    transaction.setProduit(editEvent.getNewValue());
    if (transaction.getId() > 0) editedTransactions.add(transaction);
  }
}
