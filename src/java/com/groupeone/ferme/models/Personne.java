package com.groupeone.ferme.models;

public class Personne {
  private int id;
  private String prenom;
  private String nom;
  private String email;
  private String password;
  private String image;
  private String telephone;

  public Personne(int id, String prenom, String nom, String image, String email, String password, String telephone) {
    this.id = id;
    this.prenom = prenom;
    this.nom = nom;
    this.email = email;
    this.password = password;
    this.telephone = telephone;
    this.image = image;
  }

  public Personne() {
  }

  public int getId() {
    return id;
  }

  public Personne setId(int id) {
    this.id = id;
    return this;
  }

  public String getPrenom() {
    return prenom;
  }

  public Personne setPrenom(String prenom) {
    this.prenom = prenom;
    return this;
  }

  public String getNom() {
    return nom;
  }

  public Personne setNom(String nom) {
    this.nom = nom;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public Personne setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public Personne setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getImage() {
    return image;
  }

  public Personne setImage(String image) {
    this.image = image;
    return this;
  }

  public String getTelephone() {
    return telephone;
  }

  public Personne setTelephone(String telephone) {
    this.telephone = telephone;
    return this;
  }
}
