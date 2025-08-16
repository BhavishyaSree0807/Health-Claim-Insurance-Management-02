package com.example.dao.impl;

import java.sql.*;
import java.util.*;

import com.example.dao.UserDao;
import com.example.model.User;
import com.example.util.DatabaseConnection;

public class UserDaoImpl implements UserDao {

	@Override
	public User getUser(int UId) {
		// retrieve user data with specified id from the database

		User user = null;

		// Establish Connection
		Connection con = DatabaseConnection.getConnection();

		// Write SQL query
		String query = "select * from User where userId=? ";

		// Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, UId);

			// Execute Query
			user = new User();
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getInt("userId"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				User.ROLE roletype = User.ROLE.valueOf(rs.getString("role"));
				user.setRole(roletype);
				user.setEmail(rs.getString("email"));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		// Close the connection
		finally {

		}
		return user;
	}

//---------------------------------------------------------------------------------------------------------------------//	
	@Override
	public List<User> getAllUser() {
		// retrieve data of all users from the database

		List<User> usersData = new ArrayList<>();

		// Establish Connection
		Connection con = DatabaseConnection.getConnection();

		// Write SQL query
		String query = "select * from User";

		// Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);

			// Execute query
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("userId"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				User.ROLE roletype = User.ROLE.valueOf(rs.getString("role"));
				user.setRole(roletype);
				user.setEmail(rs.getString("email"));

				usersData.add(user);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		// Close the connection
		finally {

		}

		return usersData;
	}

//---------------------------------------------------------------------------------------------------------------------//	
	@Override
	public void addUser(User user) {
		// Add user data into the database

		// Establish connection
		Connection con = DatabaseConnection.getConnection();

		// Write SQL query
		String query = """
				insert into
				User (username,password,role,email)
				values(?,?,?,?)
				""";

		// Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRole().toString());
			ps.setString(4, user.getEmail());

			// Execute query
			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

		}

	}

//---------------------------------------------------------------------------------------------------------------------//	
	@Override
	public void updateUser(User user) {
		// update user date in the database

		// Establish Connection
		Connection con = DatabaseConnection.getConnection();

		// Write SQL query
		String query = """
				update User
				set username=?,password=?,role=?,email=?
				where userId=?
				""";

		// Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRole().toString());
			ps.setString(4, user.getEmail());

			ps.setInt(5, user.getUserId());

			// Execute Query
			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		// Close the connection
		finally {

		}

	}

//---------------------------------------------------------------------------------------------------------------------//	
	@Override
	public void deleteUser(int UId) {
		// delete user date with specific id in the data

		// Establish connection
		Connection con = DatabaseConnection.getConnection();

		// Write SQL query
		String query = "delete from User where userId=?";

		// Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, UId);

			// Execute Query
			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		// close the connection
		finally {

		}

	}

}
