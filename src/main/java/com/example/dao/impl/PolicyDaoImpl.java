package com.example.dao.impl;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.example.dao.PolicyDao;
import com.example.model.Policy;
import com.example.util.DatabaseConnection;

public class PolicyDaoImpl implements PolicyDao {

	@Override
	public Policy getUser(int id) {
	    Policy user = null;
	    String query = "SELECT * FROM `policy` WHERE policyId=?";

	    try (Connection con = DatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(query)) {

	        ps.setInt(1, id);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                user = new Policy();
	                user.setPolicyId(rs.getInt("policyId"));
	                user.setPolicyNumber(rs.getString("policyNumber"));
	                user.setPolicyholderID(rs.getInt("policyholderId"));
	                user.setCoverageAmount(rs.getDouble("coverageAmount"));
	                Policy.POLICY_STATUS status = Policy.POLICY_STATUS.valueOf(rs.getString("policyStatus"));
	                user.setPolicyStatus(status);
	                user.setCreatedDate(rs.getDate("createdDate"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return user;
	}


//--------------------------------------------------------------------------------------------------------//	
	@Override
	public List<Policy> getAllUser() {
		// Retrieve data of all users from the database
		
		List<Policy> usersData = new ArrayList<>();
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query 
		String query = "select * from Policy";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			//Execute the Query
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
			Policy user = new Policy();
			user.setPolicyId(rs.getInt("policyId"));
			user.setPolicyNumber(rs.getString("policyNumber"));
			user.setPolicyholderID(rs.getInt("policyholderId"));
			user.setCoverageAmount(rs.getDouble("coverageAmount"));
			Policy.POLICY_STATUS status = Policy.POLICY_STATUS.valueOf(rs.getString("policyStatus"));
			user.setPolicyStatus(status);
			user.setCreatedDate(rs.getDate("createdDate"));
			usersData.add(user); 
		
			}

		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		//Close the connection
		finally {
			
		}
		
		return usersData;
	}

//--------------------------------------------------------------------------------------------------------//	
	@Override
	public void addUser(Policy user) {
		// Add the user data into the database (Create)
		
		//Establish the Connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query 
		String query = """
				insert into 
				Policy(policyNumber,policyholderId,coverageAmount,policyStatus,createdDate)
				values(?,?,?,?,?)
				""";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user.getPolicyNumber());
			ps.setInt(2, user.getPolicyholderID());
			ps.setDouble(3, user.getCoverageAmount());
			ps.setString(4, user.getPolicyStatus().toString());
			ps.setDate(5, (Date) user.getCreatedDate());
			
			//Execute the Query
			ps.executeUpdate();
		} 
		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//Close the Connection
		finally {
			
		}
	}

//--------------------------------------------------------------------------------------------------------//	
	@Override
	public void updateUser(Policy user) {
		// Update the user data into the database
		
		//Establish the Connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL Query
		String query = """
				update Policy
				set policyNumber=?, policyholderId=?, coverageAmount=?, policyStatus=? ,createdDate=?
				where policyId=?
				""";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, user.getPolicyNumber());
			ps.setInt(2, user.getPolicyholderID());
			ps.setDouble(3, user.getCoverageAmount());
			ps.setString(4, user.getPolicyStatus().toString());
			ps.setDate(5, (Date) user.getCreatedDate());
			ps.setInt(6, user.getPolicyId());
			
			//Execute the Query 
			ps.executeUpdate();
			
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//Close the connection
		finally {
			
		}
	}

//--------------------------------------------------------------------------------------------------------//	
	@Override
	public void deleteUser(int Pid) {
		// Delete the user data from the database
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query
		String query = "delete * from Policy where Pid=?";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, Pid);
			
			//Execute the query
			ps.executeUpdate();
			
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//Close the connection
		finally {
			
		}
	}

}
