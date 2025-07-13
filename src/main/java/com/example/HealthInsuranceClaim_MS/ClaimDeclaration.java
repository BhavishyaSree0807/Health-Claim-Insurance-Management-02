package com.example.HealthInsuranceClaim_MS;

import java.util.Date;
import java.util.List;

import com.example.dao.ClaimDao;
import com.example.dao.impl.ClaimDaoImpl;
import com.example.model.Claim;
import com.example.model.Claim.CLAIM_STATUS;

public class ClaimDeclaration {
	
	private static ClaimDao claimDao = new ClaimDaoImpl();
	private static Claim claim = new Claim();
	
	public static void createClaim() {
		
		Date utilDate = new Date();  // Current date
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		claim.setClaimDetails(2, 30000.0, sqlDate, CLAIM_STATUS.PENDING, 1);
		claimDao.addUser(claim);
		
		System.out.println("Claim created Successfully");
	
	}
//------------------------------------------------------------------------------------------------//
	
	public static void printClaim() {
		int id = 1;
		claim = claimDao.getuser(1);
		claim.printClaimDetails();
		
	}
//------------------------------------------------------------------------------------------------//
	
	public static void printAllUser() {
		List<Claim> claim = claimDao.getAllUser();
		for (int i=0; i < claim.size(); i++) {
			claim.get(i).printClaimDetails();
		}
	}
//-----------------------------------------------------------------------------------------------//
	
	public static void updateClaim() {
		
		Date utilDate = new Date();  // Current date
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		claim.setClaimDetails(2, 30000, sqlDate, CLAIM_STATUS.APPROVED, 1);
		claim.setClaimId(2);
		claimDao.updateUser(claim);
		
		System.out.println("Claim with id:" + claim.getClaimId() + " updated successfully" );
	}
//-----------------------------------------------------------------------------------------------//
	
	public static void deleteClaim() {
		
		int claimId=1;
		claimDao.deleteUser(claimId);
		
		System.out.println("Clain with id:" + claim.getClaimId() + "deleted successfully");
	}
//-----------------------------------------------------------------------------------------------//		
}
