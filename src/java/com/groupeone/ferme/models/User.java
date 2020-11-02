package com.groupeone.ferme.models;

import com.groupeone.ferme.utils.Database;
import com.groupeone.ferme.utils.RequestListener;
import com.groupeone.ferme.utils.Status;
import javafx.application.Platform;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {
  private int id;
  private String nom;
  private String email;
  private String password;
  private String image;
  private String telephone;
  private Status status;
  private long salaire;

  public int getId() {
    return id;
  }

  public static User fromResultSet(ResultSet result) throws SQLException {
    return new User()
        .setId(result.getInt("id_user"))
        .setNom(result.getString("nom"))
        .setEmail(result.getString("email"))
        .setTelephone(result.getString("telephone"))
        .setSalaire(result.getInt("salaire"))
        .setImage(result.getString("image"))
        .setPassword(result.getString("mot_de_passe"))
        .setStatus(Status.fromString(result.getString("status")));
  }

  public void login(RequestListener<User> listener) {
    Database.run(() -> {
      String sql = "SELECT * FROM users WHERE email=?,password=?,status=?;";
      try (PreparedStatement stm = Database.getInstance().prepareStatement(sql)) {
        stm.setString(1, email);
        stm.setString(2, password);
        stm.setString(3, status.label);
        ResultSet resultSet = stm.executeQuery();
        if (resultSet.next()) {
          Platform.runLater(() -> {
            try {
              listener.onResponse(User.fromResultSet(resultSet));
            } catch (SQLException throwables) {
              throwables.printStackTrace();
              Platform.runLater(() -> listener.onResponse(null));
            }
          });
        } else {
          Platform.runLater(() -> listener.onResponse(null));
        }
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    });
  }

  public static User getUserByEmail(String email) {
    String selectQuery = "SELECT email FROM users WHERE email=?";
    User user = null;
    try (PreparedStatement stm = Database.getInstance().prepareStatement(selectQuery)) {
      stm.setString(1, email);
      ResultSet resultSet = stm.executeQuery();
      if (resultSet.next()) {
        user = fromResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;
  }

  public static List<User> getItems(String sql) {
    final List<User> users = new ArrayList<>();
    try (Statement statement = Database.getInstance().createStatement()) {
      ResultSet result = statement.executeQuery(sql);
      while (result.next()) {
        users.add(User.fromResultSet(result));
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return users;
  }

  public static List<User> getEmployeesWithStatus(Status status) {
    String sql = String.format("SELECT * FROM users WHERE status != 'Propriétaire' AND status='%s'", status.label);
    return getItems(sql);
  }

  public static List<User> getEmployees() {
    String sql = "SELECT * FROM users WHERE status != 'Propriétaire'";
    return getItems(sql);
  }

  public void insert(Runnable runnable) {
    Database.run(() -> {
      try {
        String query = "INSERT INTO users (nom, email, mot_de_passe, telephone,salaire, status) VALUES (?,?,?,?,?,?)";
        prepareStatement(query).execute();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      Platform.runLater(runnable);
    });
  }

  public void update(Runnable runnable) {
    Database.run(() -> {
      try {
        String query = "UPDATE users SET nom=?, email=?, mot_de_passe=?, telephone=?,salaire=?, status=? WHERE id_user=" + id;
        prepareStatement(query).execute();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      Platform.runLater(runnable);
    });
  }

  private PreparedStatement prepareStatement(String query) throws SQLException {
    PreparedStatement stm = Database.getInstance().prepareStatement(query);
    stm.setString(1, nom);
    stm.setString(2, email);
    stm.setString(3, password);
    stm.setString(4, telephone);
    stm.setString(5, salaire + "");
    stm.setString(6, status.label);
    return stm;
  }

  public void delete(Runnable runnable) {
    Database.run(() -> {
      String deleteQuery = "DELETE FROM users WHERE id_user=?";
      try (PreparedStatement stm = Database.getInstance().prepareStatement(deleteQuery)) {
        stm.setInt(1, getId());
        stm.execute();
        Platform.runLater(runnable);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
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
