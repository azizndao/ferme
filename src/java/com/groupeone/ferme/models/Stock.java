package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Stock {
  private int id;
  private String type;
  private double quantite;
  private LocalDateTime date;

  public static Stock fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date").replace(" ", "T");
    return new Stock()
        .setId(resultSet.getInt("id"))
        .setQuantite(resultSet.getDouble("quantite"))
        .setType(resultSet.getString("type"))
        .setType(resultSet.getString("nom"))
        .setDate(LocalDateTime.parse(dateText));
  }

  public static List<Stock> recupererStock() {
    List<Stock> stocks = new ArrayList<>();
    try (Statement statement = Database.getInstance().createStatement()) {
      ResultSet resultSet = statement.executeQuery("SELECT * FROM stocks JOIN users u on u.id = stocks.personnel");
      while (resultSet.next()) {
        stocks.add(fromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return stocks;
  }

  public int getId() {
    return id;
  }

  public Stock setId(int id) {
    this.id = id;
    return this;
  }

  public String getType() {
    return type;
  }

  public Stock setType(String type) {
    this.type = type;
    return this;
  }

  public double getQuantite() {
    return quantite;
  }

  public Stock setQuantite(double quantite) {
    this.quantite = quantite;
    return this;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Stock setDate(LocalDateTime date) {
    this.date = date;
    return this;
  }
}

