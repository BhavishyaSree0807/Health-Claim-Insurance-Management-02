package com.example.model;

import java.util.Date;

public class Policy {

	public enum POLICY_STATUS {
		ACTIVE, INACTIVE, CANCELLED
	}

	private int policyId; // primary key
	private String policyNumber;
	private int policyholderID; // foreign key
	private double coverageAmount;
	private POLICY_STATUS policyStatus;
	private Date createdDate;

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public int getPolicyholderID() {
		return policyholderID;
	}

	public void setPolicyholderID(int policyholderID) {
		this.policyholderID = policyholderID;
	}

	public double getCoverageAmount() {
		return coverageAmount;
	}

	public void setCoverageAmount(double coverageAmount) {
		this.coverageAmount = coverageAmount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public POLICY_STATUS getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(POLICY_STATUS policyStatus) {
		this.policyStatus = policyStatus;
	}

	public void printPolicyDetails() {
		System.out.println(getPolicyId() + " " + getPolicyNumber() + " " + getPolicyholderID() + " "
				+ getCoverageAmount() + " " + getPolicyStatus() + " " + getCreatedDate());
	}

	public void setPolicyDetails(String policyNumber, int policyHolderId, double coverageAmount,
			POLICY_STATUS policy_status, Date date) {
		setPolicyNumber(policyNumber);
		setPolicyholderID(policyHolderId);
		setCoverageAmount(coverageAmount);
		setPolicyStatus(policy_status);
		setCreatedDate(date);

	}

}