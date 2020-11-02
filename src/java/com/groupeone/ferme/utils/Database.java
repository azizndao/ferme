package com.groupeone.ferme.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Database {

  private static Database INSTANCE;
  private static Connection connection;
  private static final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  private Database(String url, String userName, String password) throws SQLException {
    try {
      Class.forName("org.sqlite.JDBC").getConstructor().newInstance();
      connection = DriverManager.getConnection(url, userName, password);
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public static void connect(String url, String userName, String password) throws SQLException {
    if (INSTANCE == null)
      INSTANCE = new Database(url, userName, password);
  }

  public static Database getInstance() {
    return INSTANCE;
  }


  public Statement createStatement() throws SQLException {
    return connection.createStatement();
  }

  public PreparedStatement prepareStatement(String query) throws SQLException {
    return connection.prepareStatement(query);
  }

  public static void run(Runnable runnable) {
    threadPool.execute(runnable);
  }

  public static void close() throws SQLException {
    threadPool.shutdown();
    connection.close();
  }
}
