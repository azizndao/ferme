package com.groupeone.ferme.models;

public class Personnel extends Personne {
  private String salaire;

  public Personnel() {
  }

  public Personnel(int id, String prenom, String nom, String image, String email, String password, String telephone, String salaire) {
    super(id, prenom, nom, image, email, password, telephone);
    this.salaire = salaire;
  }

  public String getSalaire() {
    return salaire;
  }

  public Personnel setSalaire(String salaire) {
    this.salaire = salaire;
    return this;
  }
}
