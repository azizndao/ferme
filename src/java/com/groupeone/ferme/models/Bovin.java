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

public class Bovin {

  public static final Database DATABASE = Database.getInstance();
  private int idAnimal;
  private Sexe sexe;
  private boolean vivant;
  private Etat etat;
  private LocalDateTime dateNaissance;

  public static Bovin fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date_naissance").replace(" ", "T");
    Sexe sexe = resultSet.getString("sexe").equals("FEMELLE") ? Sexe.FEMELLE : Sexe.MALE;
    return new Bovin()
        .setIdAnimal(resultSet.getInt("id_animal"))
        .setVivant(resultSet.getBoolean("vie"))
        .setDateNaissance(LocalDateTime.parse(dateText))
        .setEtat(Etat.fromLabel(resultSet.getString("etat")))
        .setSexe(sexe);
  }

  public static void enregistrer(Set<Bovin> items, Runnable runnable) {
    Database.run(() -> {
      String sql = "INSERT INTO bovins (sexe, vie, date_naissance, etat) VALUES (?, ?, ?, ?)";
      for (Bovin item : items) {
        try (PreparedStatement stm = DATABASE.prepareStatement(sql)) {
          stm.setString(1, item.sexe.label);
          stm.setBoolean(2, item.vivant);
          stm.setString(3, item.dateNaissance.toString());
          stm.setString(4, item.etat.label);
          stm.execute();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      Platform.runLater(runnable);
    });
  }

  public static void getAll(RequestListener<List<Bovin>> listener) {
    Database.run(() -> {
      List<Bovin> boeufs = new ArrayList<>();
      try (Statement stm = DATABASE.createStatement()) {
        ResultSet resultSet = stm.executeQuery("SELECT * FROM  bovins");
        while (resultSet.next()) {
          boeufs.add(fromResultSet(resultSet));
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      listener.onResponse(boeufs);
    });
  }

  public static void update(Set<Bovin> items, Runnable runnable) {
    Database.run(() -> {
      String sql = "UPDATE bovins SET sexe=?,vie=?,etat=?,data_naissance=? WHERE id=?";
      for (Bovin item : items) {
        try (PreparedStatement stm = DATABASE.prepareStatement(sql)) {
          stm.setString(1, item.sexe.label);
          stm.setBoolean(2, item.vivant);
          stm.setString(3, item.etat.label);
          stm.setString(4, item.dateNaissance.toString());
          stm.setInt(5, item.idAnimal);
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
      String sql = "DELETE FROM bovins WHERE id_animal = ?";
      try (PreparedStatement stm = DATABASE.prepareStatement(sql)) {
        stm.setInt(1, idAnimal);
        stm.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      Platform.runLater(runnable);
    });
  }

  public int getIdAnimal() {
    return idAnimal;
  }

  public Bovin setIdAnimal(int idAnimal) {
    this.idAnimal = idAnimal;
    return this;
  }

  public Sexe getSexe() {
    return sexe;
  }

  public Bovin setSexe(Sexe sexe) {
    this.sexe = sexe;
    return this;
  }

  public boolean isVivant() {
    return vivant;
  }

  public Bovin setVivant(boolean vivant) {
    this.vivant = vivant;
    return this;
  }

  public LocalDateTime getDateNaissance() {
    return dateNaissance;
  }

  public Bovin setDateNaissance(LocalDateTime dateNaissance) {
    this.dateNaissance = dateNaissance;
    return this;
  }

  public Etat getEtat() {
    return etat;
  }

  public Bovin setEtat(Etat etat) {
    this.etat = etat;
    return this;
  }


  public enum Sexe {
    MALE("Male"), FEMELLE("Femelle");

    public final String label;

    Sexe(String label) {

      this.label = label;
    }

    public static Sexe fromLabel(String value) {
      if (value.equals("Femelle")) return Sexe.FEMELLE;
      return Sexe.MALE;
    }

    @Override
    public String toString() {
      return label;
    }
  }

  public enum Etat {
    METTRE_BAS("Mettre bas"),
    ENCEINTE("Enceinte"),
    CASTRE("Castré"),
    GENITEUR("Géniter"),
    NULL("Aucun");


    public String label;

    Etat(String label) {
      this.label = label;
    }

    public static Etat fromLabel(String label) {
      for (Etat etat : Etat.values()) {
        if (etat.label.equals(label)) return etat;
      }
      return null;
    }

    @Override
    public String toString() {
      return label;
    }
  }
}
