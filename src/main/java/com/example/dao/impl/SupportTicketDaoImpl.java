package com.example.dao.impl;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import com.example.dao.SupportTicketDao;
import com.example.model.SupportTicket;
import com.example.util.DatabaseConnection;

public class SupportTicketDaoImpl implements SupportTicketDao {

	@Override
	public SupportTicket getuesr(int STid) {
		// Retrieve user data at specified id from the database
		
		SupportTicket user = null;
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query
		String query = "select * from supportTicket where ticketid=?";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, STid);
			
			//Execute the query
			ResultSet rs = ps.executeQuery();
			user = new SupportTicket();
			if (rs.next()) {
			user.setTicketId(rs.getInt("ticketId"));
			user.setUserId(rs.getInt("userId"));
			user.setIssueDescription(rs.getString("issueDescription"));
			SupportTicket.TICKET_STATUS status = SupportTicket.TICKET_STATUS.valueOf(rs.getString("ticketStatus"));
			user.setTicketStatus(status);
			user.setCreatedDate(rs.getDate("createdDate")); }
			
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//close the connection
		finally {
			
		}
		
		return user;
	}

//---------------------------------------------------------------------------------------------------------------------//	
	@Override
	public List<SupportTicket> getAllUser() {
		// Retrieve data of all users 
		
		List<SupportTicket> usersData = new ArrayList<>();
		
		//Establish Connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL Query
		String query = "select * from SupportTicket";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			//Execute the query
			ResultSet rs = ps.executeQuery();
			
			
			while (rs.next()) {
				SupportTicket user = new SupportTicket();
			user.setTicketId(rs.getInt("ticketId"));
			user.setUserId(rs.getInt("userId"));
			user.setIssueDescription(rs.getString("issueDescription"));
			SupportTicket.TICKET_STATUS status = SupportTicket.TICKET_STATUS.valueOf(rs.getString("ticketStatus"));
			user.setTicketStatus(status);
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

//---------------------------------------------------------------------------------------------------------------------//	
	@Override
	public void addUser(SupportTicket user) {
		// Add user data into the database (create)
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query
		String query = """
				insert into 
				SupportTicket (userId,issueDescription,ticketStatus,createdDate)
				values(?,?,?,?)
				""";
		
		//Statement or PreparedStatement 
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			ps.setString(2, user.getIssueDescription());
			ps.setString(3, user.getTicketStatus().toString());
			ps.setDate(4, (Date) user.getCreatedDate());
			
			//Execute the query
			ps.executeUpdate();
			
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		// close the connection
		finally {
			
		}
	}

//---------------------------------------------------------------------------------------------------------------------//		
	@Override
	public void updateUser(SupportTicket user) {
		// Update the user data in the database
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query
		String query = """
				update SupportTicket
				set  userId=?, issueDescription=?, ticketStatus=?, createdDate=?
				where ticketId=?
				""";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			ps.setString(2, user.getIssueDescription());
			ps.setString(3, user.getTicketStatus().toString());
			ps.setDate(4, (Date) user.getCreatedDate());
			
			ps.setInt(5, user.getTicketId());
		
		//Execute the query
			ps.executeUpdate();
			
			
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
		
		// Close the connection
		finally {
			
		}
	}

//---------------------------------------------------------------------------------------------------------------------//		
	@Override
	public void deleteUser(int STid) {
		// Delete the specified user data from the database
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query 
		String query = "delete * from SupportTicket where id=?";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, STid);
			
			//Execute Query
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
