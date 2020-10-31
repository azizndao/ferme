package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Diagnostic {
  private int id;
  private String maladie;
  private int idAnimal;
  private String personnel;
  private String description;
  private LocalDateTime date;

  public static Diagnostic fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date_diagnostic").replace(" ", "T");
    return new Diagnostic()
        .setId(resultSet.getInt("id_diagnostic"))
        .setMaladie(resultSet.getString("maladie"))
        .setDescription(resultSet.getString("description"))
        .setPersonnel(resultSet.getString("nom"))
        .setDate(LocalDateTime.parse(dateText));
  }

  public static List<Diagnostic> recupererDiagnostics() {
    List<Diagnostic> diagnostics = new ArrayList<>();
    try (Statement stm = Database.getInstance().createStatement()) {
      ResultSet resultSet = stm.executeQuery("SELECT * FROM `diagnostics` JOIN users ON `diagnostics`.personnel = users.id");
      while (resultSet.next()) {
        diagnostics.add(fromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return diagnostics;
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

  public String getPersonnel() {
    return personnel;
  }

  public Diagnostic setPersonnel(String personnel) {
    this.personnel = personnel;
    return this;
  }
}
