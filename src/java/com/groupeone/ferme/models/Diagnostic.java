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

public class Diagnostic {
  public static final Database DATABASE = Database.getInstance();
  private int id;
  private String maladie;
  private User personnel;
  private String description;
  private Bovin bovin;
  private LocalDateTime date;

  public static Diagnostic fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date_diagnostic").replace(" ", "T");
    User user = User.fromResultSet(resultSet);
    Bovin bovin = Bovin.fromResultSet(resultSet);
    return new Diagnostic()
        .setId(resultSet.getInt("id_diagnostic"))
        .setMaladie(resultSet.getString("maladie"))
        .setDescription(resultSet.getString("description"))
        .setPersonnel(user)
        .setAnimal(bovin)
        .setDate(LocalDateTime.parse(dateText));
  }

  public static void getAll(RequestListener<List<Diagnostic>> listener) {
    Database.run(() -> {
      List<Diagnostic> diagnostics = new ArrayList<>();
      try (Statement stm = Database.getInstance().createStatement()) {
        String sql = "SELECT * FROM `diagnostics` JOIN users ON `diagnostics`.id_user = users.id_user JOIN bovins ON diagnostics.id_animal = bovins.id_animal";
        ResultSet resultSet = stm.executeQuery(sql);
        while (resultSet.next()) {
          diagnostics.add(fromResultSet(resultSet));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      Platform.runLater(() -> listener.onResponse(diagnostics));
    });
  }

  public static void update(Set<Diagnostic> diagnostics, Runnable runnable) {
    Database.run(() -> {
      String sql = "UPDATE TABLE diagnostics SET maladie=?, id_user=?, idAnimal=?, description=?";
      for (Diagnostic item : diagnostics) {
        try (PreparedStatement stm = DATABASE.prepareStatement(sql)) {
          stm.setString(1, item.maladie);
          stm.setInt(2, item.personnel.getId());
          stm.setInt(3, item.bovin.getIdAnimal());
          stm.setString(4, item.description);
          stm.execute();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
      Platform.runLater(runnable);
    });
  }

  public static void add(Set<Diagnostic> items, Runnable runnable) {
    Database.run(() -> {
      String sql = "INSERT INTO diagnostics(maladie,id_animal,id_user,description) VALUES (?,?,?,?)";
      for (Diagnostic item : items) {
        try (PreparedStatement stm = DATABASE.prepareStatement(sql)) {
          stm.setString(1, item.maladie);
          stm.setInt(2, item.bovin.getIdAnimal());
          stm.setInt(3, item.personnel.getId());
          stm.setString(4, item.description);
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
      try (Statement statement = DATABASE.createStatement()) {
        String sql = String.format("DELETE FROM diagnostics WHERE id_diagnostic = %s", id);
        statement.execute(sql);
      } catch (SQLException e) {
        e.printStackTrace();
      }
      Platform.runLater(runnable);
    });
  }

  public int getId() {
    return id;
  }

  public Diagnostic setId(int id) {
    this.id = id;
    return this;
  }

  public String getMaladie() {
    return maladie;
  }

  public Diagnostic setMaladie(String maladie) {
    this.maladie = maladie;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Diagnostic setDescription(String description) {
    this.description = description;
    return this;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public Diagnostic setDate(LocalDateTime date) {
    this.date = date;
    return this;
  }

  public User getPersonnel() {
    return personnel;
  }

  public Diagnostic setPersonnel(User personnel) {
    this.personnel = personnel;
    return this;
  }

  public Bovin getAnimal() {
    return bovin;
  }

  public Diagnostic setAnimal(Bovin bovin) {
    this.bovin = bovin;
    return this;
  }
}
