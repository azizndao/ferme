package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;
import com.groupeone.ferme.utils.RequestListener;
import javafx.application.Platform;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Transaction {
  public static final Database DATABASE = Database.getInstance();
  private int id;
  private Type type;
  private LocalDateTime date;
  private double somme;
  private String produit;
  private User personnel;

  public static void getAll(RequestListener<List<Transaction>> listener) {
    Database.run(() -> {
      List<Transaction> transactions = new ArrayList<>();
      try (Statement stm = Database.getInstance().createStatement()) {
        String sql = "SELECT * FROM transactions JOIN users u on u.id_user = transactions.id_user";
        ResultSet resultSet = stm.executeQuery(sql);
        while (resultSet.next()) {
          transactions.add(fromResultSet(resultSet));
        }
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      Platform.runLater(() -> listener.onResponse(transactions));
    });
  }

  private static Transaction fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date").replace(" ", "T");
    return new Transaction()
        .setType(Type.fromLabel(resultSet.getString("type")))
        .setProduit(resultSet.getString("produit"))
        .setSomme(resultSet.getDouble("somme"))
        .setDate(LocalDateTime.parse(dateText))
        .setId(resultSet.getInt("id_transaction"))
        .setPersonnel(User.fromResultSet(resultSet));
  }

  public static void enregistrer(Set<Transaction> items, Runnable runnable) {
    Database.run(() -> {
      String sql = "INSERT INTO transactions (type, date, somme, produit, id_user) VALUES (?,?,?,?,?)";
      for (Transaction item : items) {
        try (PreparedStatement stm = DATABASE.prepareStatement(sql)) {
          stm.setString(1, item.type.label);
          stm.setString(2, item.date.toString());
          stm.setDouble(3, item.somme);
          stm.setString(4, item.produit);
          stm.setInt(5, item.personnel.getId());
          stm.execute();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      Platform.runLater(runnable);
    });
  }

  public static void update(Set<Transaction> items, Runnable runnable) {
    Database.run(() -> {
      String sql = "UPDATE transactions SET type=?,somme=?,produit=? WHERE id_transaction=?";
      for (Transaction item : items) {
        try (PreparedStatement stm = DATABASE.prepareStatement(sql)) {
          stm.setString(1, item.type.label);
          stm.setDouble(2, item.somme);
          stm.setString(3, item.produit);
          stm.setInt(4, item.id);
          stm.execute();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
      Platform.runLater(runnable);
    });
  }

  public void delete(Runnable runnable) {
    Database.run(() -> {
      try (Statement stm = Database.getInstance().createStatement()) {
        stm.execute(String.format("DELETE FROM transactions WHERE id_transaction=%s", id));
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      Platform.runLater(runnable);
    });
  }

  public int getId() {
    return id;
  }

  public Transaction setId(int id) {
    this.id = id;
    return this;
  }

  public Type getType() {
    return type;
  }

  public Transaction setType(Type type) {
    this.type = type;
    return this;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Transaction setDate(LocalDateTime date) {
    this.date = date;
    return this;
  }

  public double getSomme() {
    return somme;
  }

  public Transaction setSomme(double somme) {
    this.somme = somme;
    return this;
  }

  public String getProduit() {
    return produit;
  }

  public Transaction setProduit(String produit) {
    this.produit = produit;
    return this;
  }

  public User getPersonnel() {
    return personnel;
  }

  public Transaction setPersonnel(User personnel) {
    this.personnel = personnel;
    return this;
  }

  public enum Type {
    VENTE("Vente"),
    ACHAT("Achat");

    public final String label;

    Type(String label) {
      this.label = label;
    }

    public static Type fromLabel(String label) {
      if (label.equals("Achat")) return Type.ACHAT;
      return Type.VENTE;
    }

    @Override
    public String toString() {
      return label;
    }
  }
}
