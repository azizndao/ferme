package com.groupeone.ferme.utils;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

/**
 * Cette variable est utilisée pour aider notre {@link ComboBox <Status>} à représenter
 * les objets de type {@link Status} dans la fenêtre
 */
public class StatusConverter extends StringConverter<Status> {

  /**
   * Cette methode sera appelée quant le {@link ComboBox<Status>} veut affiche un {@link Status}
   * Avec cette methode nous disons à la {@link ComboBox<Status>}: quant tu voit un {@link Status}
   * affiche son label
   *
   * @param status l'élément que le {@link ComboBox<Status>} est entrain d'affiché
   * @return elle retourne la valeur à affiche et qui represente l'élément sélectionné
   */
  @Override
  public String toString(Status status) {
    if (status == null) return null;
    return status.label;
  }

  /**
   * Cette methode va faire l'inverse de la methode toString(Status). Elle va prendre la chaine de caractères
   * affichée et déduire la table correspondant
   *
   * @param label le label de l'élément {@link Status} sélectionné
   * @return elle retourne {@link Status} correspondant
   */
  @Override
  public Status fromString(String label) {
    return Status.valueOf(label);
  }
}