package com.groupeone.ferme.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Database {
	
	private static Database INSTANCE;
	private static Connection connection;
	
	private Database(String url, String userName, String password) throws SQLException {
		try {
			Class.forName("org.mariadb.jdbc.Driver").getConstructor().newInstance();
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

	public static void close() throws SQLException {
		connection.close();
	}

  public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}
	
	public PreparedStatement preparedStatement(String query) throws SQLException {
		return connection.prepareStatement(query);
	}

}
