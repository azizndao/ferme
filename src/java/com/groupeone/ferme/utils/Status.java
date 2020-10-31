package com.groupeone.ferme.utils;


import javafx.scene.control.ComboBox;

import java.util.Arrays;

/**
 * Cette enum est utilisée dans notre {@link ComboBox}
 * Elle nous permet de couplé chaque option avec une label que l'on affiche et une table
 * qui est la table dans nous base de données où cet utilisateur est enregistré
 * Par exemple si on est le propriétaire de la ferme on se connecte entente que propriétaire
 * on le récupérera la table correspondante pour y effectuer les verification
 */
public enum Status {
  PROPRIETAIRE("Propriétaire", "*"),
  GERANT_STOCK("Gérant Stock", "Stock"),
  BOUTIQUIER("Boutiquier", "Transactions"),
  VETERINAIRE("Vétérinaire", "Sante"),
  ALL("Tous les employés", "*");

  public final String label;
  public final String access;

  Status(String label, String access) {
    this.label = label;
    this.access = access;
  }

  public static Status fromString(String label) {
    if (label.equals("Propriétaire")) return Status.PROPRIETAIRE;
    if (label.equals("Gérant Stock")) return Status.GERANT_STOCK;
    if (label.equals("Boutiquier")) return Status.BOUTIQUIER;
    return Status.VETERINAIRE;
  }
}
