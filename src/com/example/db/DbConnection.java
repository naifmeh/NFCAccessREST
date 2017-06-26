package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	
	public Connection getConnection() throws Exception {
		try{
			String connectionURL = "jdbc:mysql://127.0.0.1:3307/authaccess";
			Connection connection = null;
			System.out.println("Trying to connect");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL,"root","test");
			
			return connection;
		} catch(SQLException e) {
			throw e;
		} catch(Exception e) {
			throw e;
		}
	}
}
