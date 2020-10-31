package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Boeuf extends Animal {
  private int idBoeuf;
  private boolean castre;


  public static Boeuf fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date_naissance").replace(" ", "T");
    return (Boeuf) new Boeuf()
        .setIdBoeuf(resultSet.getInt("id_boeuf"))
        .setCastre(resultSet.getBoolean("castre"))
        .setIdAnimal(resultSet.getInt("id_animal"))
        .setVivant(resultSet.getBoolean("vie"))
        .setDateNaissance(LocalDateTime.parse(dateText))
        .setSexe(Sexe.MALE);
  }


  public static List<Boeuf> recupererVaches() {
    List<Boeuf> boeufs = new ArrayList<>();
    try (Statement stm = Database.getInstance().createStatement()) {
      ResultSet resultSet = stm.executeQuery("SELECT * FROM boeufs JOIN animals a on a.id_animal = boeufs.id_animal");
      while (resultSet.next()) {
        boeufs.add(fromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return boeufs;
  }


  public int getIdBoeuf() {
    return idBoeuf;
  }

  public Boeuf setIdBoeuf(int idBoeuf) {
    this.idBoeuf = idBoeuf;
    return this;
  }

  public boolean isCastre() {
    return castre;
  }

  public Boeuf setCastre(boolean castre) {
    this.castre = castre;
    return this;
  }
}
