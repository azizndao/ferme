package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;
import com.groupeone.ferme.utils.RequestListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Diagnostic {
  private int id;
  private String maladie;
  private int idAnimal;
  private User personnel;
  private String description;
  private Animal animal;
  private LocalDateTime date;

  public static Diagnostic fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date_diagnostic").replace(" ", "T");
    User user = User.fromResultSet(resultSet);
    Animal animal = Animal.fromResultSet(resultSet);
    return new Diagnostic()
        .setId(resultSet.getInt("id_diagnostic"))
        .setMaladie(resultSet.getString("maladie"))
        .setDescription(resultSet.getString("description"))
        .setPersonnel(user)
        .setAnimal(animal)
        .setDate(LocalDateTime.parse(dateText));
  }

  public static void getAll(RequestListener<List<Diagnostic>> listener) {
    try (Statement stm = Database.getInstance().createStatement()) {
      List<Diagnostic> diagnostics = new ArrayList<>();
      String sql = "SELECT * FROM `diagnostics` JOIN users ON `diagnostics`.id_user = users.id_user JOIN animals ON diagnostics.id_animal = animals.id_animal";
      ResultSet resultSet = stm.executeQuery(sql);
      while (resultSet.next()) {
        diagnostics.add(fromResultSet(resultSet));
      }
      listener.onResponse(diagnostics);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void update(Set<Diagnostic> diagnostics, Runnable runnable) {
    Database.run(() -> {
      String sql = "UPDATE TABLE diagnostics SET maladie=?, id_user=?, idAnimal=?, description=?";
      for (Diagnostic item : diagnostics) {
        try (PreparedStatement stm = Database.getInstance().prepareStatement(sql)) {
          stm.setString(1, item.maladie);
          stm.setInt(2, item.personnel.getId());
          stm.setInt(3, item.idAnimal);
          stm.setString(4, item.description);
          stm.execute();
        } catch (SQLException throwables) {
          throwables.printStackTrace();
        }
      }
      runnable.run();
    });
  }

  public static void add(Set<Diagnostic> diagnostics) {
    String sql = "INSERT INTO diagnostics(maladie,idAnimal, id_user, description) VALUES ";
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

  public int getIdAnimal() {
    return idAnimal;
  }

  public Diagnostic setIdAnimal(int idAnimal) {
    this.idAnimal = idAnimal;
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

  public Animal getAnimal() {
    return animal;
  }

  public Diagnostic setAnimal(Animal animal) {
    this.animal = animal;
    return this;
  }
}
