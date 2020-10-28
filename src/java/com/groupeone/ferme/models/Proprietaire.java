package com.groupeone.ferme.models;

public class Proprietaire extends Personne {

  public Proprietaire() {
  }

  public Proprietaire(int id, String prenom, String nom, String image, String email, String password, String telephone, String avatar) {
    super(id, prenom, nom, image, email, password, telephone, avatar, null);
  }
}
