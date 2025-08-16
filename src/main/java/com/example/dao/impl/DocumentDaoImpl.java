package com.example.dao.impl;

import java.sql.*;
import java.util.*;

import com.example.dao.*;
import com.example.model.Document;
import com.example.util.DatabaseConnection;

public class DocumentDaoImpl implements DocumentDao {

	@Override
	public Document getuesr(int docid) {
		// Retrieve user data at specified id from database
		Document user = null;
		//Establish Connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query
		String query = "select * from Document where documentid=?";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, docid);
			
			//Execute Query 
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new Document();
				user.setDocumentId(rs.getInt("documentId"));
				user.setClaimId(rs.getInt("claimId"));
				user.setDocumentName(rs.getString("documentName"));
				user.setDocumentPath(rs.getString("documentPath"));
				Document.Document_Type format = Document.Document_Type.valueOf(rs.getString("documentType"));
				user.setDocumentType(format);
			}
			
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		//Close the connection
		finally {
			
		}
		
		return user;
	}

//-------------------------------------------------------------------------------------------//	
	@Override
	public List<Document> getAllUser() {
		// Retrieve data of all users from database
		List<Document> usersData = new ArrayList<>();
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query 
		String query="select * from Document";
		
		//Statement or PreparedStaement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			//Execute the query
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Document user = new Document();
				user.setDocumentId(rs.getInt("documentId"));
				user.setClaimId(rs.getInt("claimId"));
				user.setDocumentName(rs.getString("documentName"));
				user.setDocumentPath(rs.getString("documentPath"));
				Document.Document_Type format = Document.Document_Type.valueOf(rs.getString("documentType"));
				user.setDocumentType(format);
				usersData.add(user);
			}
			
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		//close the connection 
		finally {
			
		}
		return usersData;
	}

//-------------------------------------------------------------------------------------------//	
	@Override
	public void addUser(Document user) {
		// Add the user data into the database
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query
		String query = """
				insert into
				Document (claimId,documentName,documentPath,documentType)
				values(?,?,?,?)
				""";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, user.getClaimId());
			ps.setString(2, user.getDocumentName());
			ps.setString(3, user.getDocumentPath());
			ps.setString(4, user.getDocumentType().toString());
			
			//Execute the query
			ps.executeUpdate();
			
			
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}
		
		//close the connection
		finally{
			
		}
		
	}
//-------------------------------------------------------------------------------------------//
	@Override
	public void updateUser(Document user) {
		// Update the user data into the database
		
		//Establish Connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL query 
		String query = """
				update Document
				set claimId=?, documentName=? ,documentPath=? ,documentType=?
				where documentId=?
				""";
		
		//Statement or PreparedStatement
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, user.getClaimId());
			ps.setString(2, user.getDocumentName());
			ps.setString(3, user.getDocumentPath());
			ps.setString(4, user.getDocumentType().toString());
			ps.setInt(5, user.getDocumentId());
			
			//Execute the query
			ps.executeUpdate();
			
			
		} 
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		//Close the Connection
		finally {
			
		}
		
	}

//-------------------------------------------------------------------------------------------//	
	@Override
	public void deleteUser(int docid) {
		// Delete user data from database
		
		//Establish the connection
		Connection con = DatabaseConnection.getConnection();
		
		//Write SQL Query 
		String query = "delete * from Documents where id=?";
		
		//Statement or PreparedStatemnet
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, docid);
			
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
