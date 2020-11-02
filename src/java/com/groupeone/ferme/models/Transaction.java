package com.groupeone.ferme.models;

import java.time.LocalDateTime;

public class Transaction {
  private int id;
  private TypeTransaction type;
  private LocalDateTime date;
  private double somme;
  private String produit;

  public enum TypeTransaction {
    VENTE("Vente"),
    ACHAT("Achat");

    private final String label;

    TypeTransaction(String label) {

      this.label = label;
    }
  }
}
