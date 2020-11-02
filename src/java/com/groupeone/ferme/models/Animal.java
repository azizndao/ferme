package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class Animal {
  public static final Database DATABASE = Database.getInstance();
  private int idAnimal;
  private Sexe sexe;
  private boolean vivant;
  private LocalDateTime dateNaissance;


  public static Animal fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date_naissance").replace(" ", "T");
    return new Animal()
        .setIdAnimal(resultSet.getInt("id_animal"))
        .setVivant(resultSet.getBoolean("vie"))
        .setDateNaissance(LocalDateTime.parse(dateText))
        .setSexe(Sexe.FEMELLE);
  }


  public int getIdAnimal() {
    return idAnimal;
  }

  public Animal setIdAnimal(int idAnimal) {
    this.idAnimal = idAnimal;
    return this;
  }

  public Sexe getSexe() {
    return sexe;
  }

  public Animal setSexe(Sexe sexe) {
    this.sexe = sexe;
    return this;
  }

  public boolean isVivant() {
    return vivant;
  }

  public Animal setVivant(boolean vivant) {
    this.vivant = vivant;
    return this;
  }

  public LocalDateTime getDateNaissance() {
    return dateNaissance;
  }

  public Animal setDateNaissance(LocalDateTime dateNaissance) {
    this.dateNaissance = dateNaissance;
    return this;
  }

  protected int enregistrer() {
    int id = 0;
    String sql = "INSERT INTO animal (sexe, vie, date_naissance) VALUES (?, ?, ?)";
    try (PreparedStatement stm = DATABASE.prepareStatement(sql)) {
      stm.setString(1, sexe.label);
      stm.setBoolean(2, vivant);
      stm.setString(3, dateNaissance.toString());
      stm.execute();
      Statement selectLast = DATABASE.createStatement();
      ResultSet resultSet = selectLast.executeQuery("SELECT id_animal FROM animals ORDER BY id_animal DESC LIMIT 1;");
      if (resultSet.next()) {
        id = resultSet.getInt("id_animal");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  public enum Sexe {
    MALE("Male"), FEMELLE("Femelle");

    public final String label;

    Sexe(String label) {

      this.label = label;
    }
  }
}
