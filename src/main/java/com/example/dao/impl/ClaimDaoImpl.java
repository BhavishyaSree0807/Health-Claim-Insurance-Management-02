package com.example.dao.impl;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.example.dao.*;
import com.example.model.Claim;
import com.example.util.DatabaseConnection;

public class ClaimDaoImpl implements ClaimDao {

	@Override
	public Claim getuser(int claimid) {
		// Get data for a user with specified id 
		Claim user = null;
		
		//Write SQL query
		String query = "select * from Claim where claimid=? ";
		
		//Establish connection
		try (Connection con = DatabaseConnection.getConnection();
			//Statement or PreparedStatement
			PreparedStatement ps = con.prepareCall(query)){
			
			ps.setInt(1, claimid); 
			
			// Execute the query 
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new Claim();
				user.setClaimId(rs.getInt("claimId"));
				user.setPolicyId(rs.getInt("policyId"));
				user.setClaimAmount(rs.getDouble("claimAmount"));
				user.setAdjusterID(rs.getInt("adjusterId"));
				user.setClaimDate(rs.getDate("claimDate"));
				Claim.CLAIM_STATUS status = Claim.CLAIM_STATUS.valueOf(rs.getString("claimStatus"));
				user.setClaimStatus(status);
				}
			
			}
		catch(Exception e) {
			e.printStackTrace();
		}
		//close the connection
		finally {
			//rs.close();
		}
		
		return user;
	}

//-------------------------------------------------------------------------------------------//
	@Override
	public List<Claim> getAllUser() {
		// Get data of all users
		List<Claim> usersData = new ArrayList<>();
		
		//Write SQL query 
		String query = "Select * from Claim";
		
		//Establish Connection
		try (Connection con = DatabaseConnection.getConnection();
				//Statement or PreparedStatement
				PreparedStatement ps = con.prepareCall(query)){
				 
			    //Execute query 
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Claim user = new Claim();
					user.setClaimId(rs.getInt("claimId"));
					user.setPolicyId(rs.getInt("policyId"));
					user.setClaimAmount(rs.getDouble("claimAmount"));
					user.setAdjusterID(rs.getInt("adjusterId"));
					user.setClaimDate(rs.getDate("claimDate"));
					Claim.CLAIM_STATUS status = Claim.CLAIM_STATUS.valueOf(rs.getString("claimStatus"));
					user.setClaimStatus(status);
					
					usersData.add(user);
					}
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		
		    //Close the connection
			finally {
//				rs.close();
			}
		return usersData;
	}

//-------------------------------------------------------------------------------------------//	
	// todo: close the connections
	@Override
	public void addUser(Claim user) {
		// Add user data into database
		
		// Establish connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL Query
		String query = """ 
				insert into 
				Claim(policyId,claimAmount,claimDate,claimStatus,adjusterId)
				values(?,?,?,?,?)
				""";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, user.getPolicyId());
			ps.setDouble(2, user.getClaimAmount());
			ps.setDate(3, (Date) user.getClaimDate());
			ps.setString(4, user.getClaimStatus().toString());
			ps.setInt(5, user.getAdjusterID());
			
			//ExecuteQuery
			ps.executeUpdate();
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		//Close the connection
		finally {
			
		}
	}
	
//-------------------------------------------------------------------------------------------//
	@Override
	public void updateUser(Claim user) {
		// Update the user data in database
		
		//Establish connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL Query
		String query = """
				Update Claim 
				set policyId=?, claimAmount=?, claimDate=?, claimStatus=?, adjusterId=? 
				where claimId=?
				""";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, user.getPolicyId());
			ps.setDouble(2, user.getClaimAmount());
			ps.setDate(3, (Date) user.getClaimDate());
			ps.setString(4, user.getClaimStatus().toString());
			ps.setInt(5, user.getAdjusterID());
			ps.setInt(6, user.getClaimId());
			
			//Execute the query
			ps.executeUpdate();
			
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		//close the connection
		finally {
			
		}
		
		
	}

//-------------------------------------------------------------------------------------------//
	@Override
	public void deleteUser(int claimid) {
		// Delete user data from the database
		
		//Establish connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL Query 
		String query = "delete * from Claim where claimid = ?";
		
		//Statement or PreparedStatement 
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, claimid);
			
			//Execute the query
			ps.executeUpdate();
			
		} 
		catch (SQLException e) {
		
			e.printStackTrace();
		}
		//close the connection		
		finally {
			
		}
	}

}
