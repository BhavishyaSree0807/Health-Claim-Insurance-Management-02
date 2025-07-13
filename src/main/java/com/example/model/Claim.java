package com.example.model;

import java.util.Date;

import com.example.model.Policy.POLICY_STATUS;



public class Claim {
	
	public enum CLAIM_STATUS {
		PENDING,
		APPROVED,
		REJECTED
	}
	
	private int claimId; //primary key
	private int policyId; //foreign key
	private double claimAmount;
	private Date claimDate;
	private CLAIM_STATUS claimStatus;
	private int adjusterID; //foreign key
	
	
	public int getClaimId() {
		return claimId;
	}
	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}
	
	
	public int getPolicyId() {
		return policyId;
	}
	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
	
	
	public double getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}
	
	
	public int getAdjusterID() {
		return adjusterID;
	}
	public void setAdjusterID(int adjusterID) {
		this.adjusterID = adjusterID;
	}
	
	
	public Date getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}
	
	
	public CLAIM_STATUS getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(CLAIM_STATUS claimStatus) {
		this.claimStatus = claimStatus;
	}
	

	public void printClaimDetails() {
		System.out.println(getClaimId() + " " + getPolicyId() + " " + getClaimAmount() + " " + 
	                      getClaimDate()+ " " + getClaimStatus() + " " + getAdjusterID());
	}
	
	public void setClaimDetails(int policyId, double claimAmount,Date claimDate, CLAIM_STATUS claimStatus, int adjusterID) {
		
		setPolicyId(policyId);
		setClaimAmount(claimAmount);
		setClaimDate(claimDate);
		setClaimStatus(claimStatus);
		setAdjusterID(adjusterID);
	}


}
