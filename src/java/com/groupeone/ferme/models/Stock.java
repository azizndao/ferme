package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;
import com.groupeone.ferme.utils.RequestListener;
import javafx.application.Platform;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

public class Stock {
  public static final Database DATABASE = Database.getInstance();
  private int id;
  private String type;
  private User personnel;
  private double quantite;
  private LocalDateTime date;

  public static Stock fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date").replace(" ", "T");
    User personnel = User.fromResultSet(resultSet);
    return new Stock()
        .setId(resultSet.getInt("id_stock"))
        .setQuantite(resultSet.getDouble("quantite"))
        .setType(resultSet.getString("type"))
        .setPersonnel(personnel)
        .setDate(LocalDateTime.parse(dateText));
  }

  public static void getAll(RequestListener<List<Stock>> listener) {
    Database.run(() -> {
      List<Stock> stocks = new ArrayList<>();
      try (Statement statement = DATABASE.createStatement()) {
        String sql = "SELECT * FROM stocks JOIN users u on u.id_user = stocks.id_user";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
          stocks.add(fromResultSet(resultSet));
        }
        listener.onResponse(stocks);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
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

  public User getPersonnel() {
    return personnel;
  }

  public Stock setPersonnel(User personnel) {
    this.personnel = personnel;
    return this;
  }

  public void add(Runnable runnable) {
    Database.run(() -> {

    });
  }

  public static void add(Collection<Stock> items, Runnable runnable) {
    Database.run(() -> {
      for (Stock stock : items) {
        String sql = "INSERT INTO stocks (type, quantite, id_user) VALUES (?,?,?);";
        try (PreparedStatement statement = DATABASE.prepareStatement(sql)) {
          statement.setString(1, stock.type);
          statement.setDouble(2, stock.quantite);
          statement.setInt(3, stock.personnel.getId());
          statement.execute();
          Platform.runLater(runnable);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      Platform.runLater(runnable);
    });
  }

  public static void update(Set<Stock> items, Runnable runnable) {
    Database.run(() -> {
      for (Stock stock : items) {
        String sql = "UPDATE TABLE stocks SET type=?,quantite=?";
        try (PreparedStatement statement = DATABASE.prepareStatement(sql)) {
          statement.setString(1, stock.type);
          statement.setDouble(2, stock.quantite);
          statement.execute();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      Platform.runLater(runnable);
    });
  }

  public void delete (Runnable runnable) {
    Database.run(() -> {
      try (Statement statement = DATABASE.createStatement()) {
        String sql = String.format("DELETE FROM stocks WHERE id_stock = %s", id);
        statement.execute(sql);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      Platform.runLater(runnable);
    });
  }
}

