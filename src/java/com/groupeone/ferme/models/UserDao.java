package com.groupeone.ferme.models;

import me.abdou.reflector.annotations.Insert;
import me.abdou.reflector.annotations.Query;

import java.util.List;

public interface UserDao {
  @Insert
  void insertUser(User... users);

  @Query("SELECT * FROM users")
  List<User> getUsers();
}
