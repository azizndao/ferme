package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Vache extends Animal {
  private int idVache;
  private Etat etat;

  public static Vache fromResultSet(ResultSet resultSet) throws SQLException {
    String dateText = resultSet.getString("date_naissance").replace(" ", "T");
    return (Vache) new Vache()
        .setIdVache(resultSet.getInt("id_vache"))
        .setEtat(Etat.fromLabel(resultSet.getString("etat")))
        .setIdAnimal(resultSet.getInt("id_animal"))
        .setVivant(resultSet.getBoolean("vie"))
        .setDateNaissance(LocalDateTime.parse(dateText))
        .setSexe(Sexe.FEMELLE);
  }

  public static List<Vache> recupererVaches() {
    List<Vache> vaches = new ArrayList<>();
    try (Statement stm = Database.getInstance().createStatement()) {
      ResultSet resultSet = stm.executeQuery("SELECT * FROM vaches JOIN animals a on a.id_animal = vaches.id_animal");
      while (resultSet.next()) {
        vaches.add(fromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return vaches;
  }

  @Override
  public int enregistrer() {
    int idAnimal = super.enregistrer();
    int idVache = 0;
    String query = "INSERT INTO vaches (etat, animal) VALUES (?,?)";
    try (PreparedStatement stm = Database.getInstance().prepareStatement(query)) {
      stm.setString(1, etat.label);
      stm.setInt(2, idAnimal);
      stm.execute();
      Statement selectLast = DATABASE.createStatement();
      ResultSet resultSet = selectLast.executeQuery("SELECT id_vache FROM vaches ORDER BY id_vache DESC LIMIT 1;");
      if (resultSet.next()) {
        idVache = resultSet.getInt("id_vache");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    setIdAnimal(idAnimal);
    setIdVache(idVache);
    return idVache;
  }

  public int getIdVache() {
    return idVache;
  }

  public Vache setIdVache(int idVache) {
    this.idVache = idVache;
    return this;
  }

  public Etat getEtat() {
    return etat;
  }

  public Vache setEtat(Etat etat) {
    this.etat = etat;
    return this;
  }

  public enum Etat {
    METTRE_BAS("Mettre bas"),
    GROSSESSE("Enceinte"),
    NULL("Aucun");


    private String label;

    Etat(String label) {
      this.label = label;
    }

    public static Etat fromLabel(String label) {
      for (Etat etat : Etat.values()) {
        if (etat.label.equals(label)) return etat;
      }
      return null;
    }
  }
}
