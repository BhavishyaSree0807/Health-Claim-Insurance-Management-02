package com.example.util;

import java.sql.*;


public class DatabaseConnection {
	
	static final String db_url = "jdbc:mysql://localhost:3306/projectschema";
	static final String user = "root";
	static final String pswd = "root";
	static final String query = "select * from user";
	
	static Connection con = null;
	
	static {
		try {
			con = DriverManager.getConnection(db_url, user, pswd);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	} 

}
