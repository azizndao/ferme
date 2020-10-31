package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
  private int id;
  private String nom;
  private String email;
  private String password;
  private String image;
  private String telephone;
  private Status status;
  private long salaire;

  public User() {
  }

  public int getId() {
    return id;
  }


  public static User fromResultSet(ResultSet result) throws SQLException {
    User a = new User();
    return new User()
        .setId(result.getInt("id"))
        .setNom(result.getString("nom"))
        .setEmail(result.getString("email"))
        .setTelephone(result.getString("telephone"))
        .setSalaire(result.getInt("salaire"))
        .setImage(result.getString("image"))
        .setPassword(result.getString("mot_de_passe"))
        .setStatus(Status.fromString(result.getString("status")));
  }


  public User setId(int id) {
    this.id = id;
    return this;
  }

  public String getNom() {
    return nom;
  }

  public User setNom(String nom) {
    this.nom = nom;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getImage() {
    return image;
  }

  public User setImage(String image) {
    this.image = image;
    return this;
  }

  public String getTelephone() {
    return telephone;
  }

  public User setTelephone(String telephone) {
    this.telephone = telephone;
    return this;
  }

  public Status getStatus() {
    return status;
  }

  public User setStatus(Status status) {
    this.status = status;
    return this;
  }

  public long getSalaire() {
    return salaire;
  }

  public User setSalaire(long salaire) {
    this.salaire = salaire;
    return this;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", nom='" + nom + '\'' +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", image='" + image + '\'' +
        ", telephone='" + telephone + '\'' +
        ", status=" + status +
        ", salaire=" + salaire +
        '}';
  }
}
