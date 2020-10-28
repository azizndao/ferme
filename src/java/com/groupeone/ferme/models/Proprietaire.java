package com.groupeone.ferme.models;

public class Proprietaire extends Personne {

  public Proprietaire() {
  }

  public Proprietaire(int id, String prenom, String nom, String image, String email, String password, String telephone, String salaire, int id1, String prenom1, String nom1, String email1, String password1) {
    super(id, prenom, nom, image, email, password, telephone);
  }
}
