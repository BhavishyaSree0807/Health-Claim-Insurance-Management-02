package com.example.HealthInsuranceClaim_MS;

import com.example.model.Policy;
import com.example.model.Policy.POLICY_STATUS;
import com.example.dao.impl.*;

import java.util.Date;
import java.util.List;

import com.example.dao.*;

public class PolicyDeclaration {
	
	private static PolicyDao policyDao = new PolicyDaoImpl();
	private static Policy policy = new Policy();
	
	public static void createPolicy() {
		
		Date utilDate = new Date();  // Current date
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	
		policy.setPolicyDetails("Policy-002", 1, 20000.0, POLICY_STATUS.ACTIVE, sqlDate);
		policyDao.addUser(policy);
		
		System.out.println("Policy created Successfully");
	}
//---------------------------------------------------------------------------------------------------//
	public static void printPolicy() {
		
		int policyId = 1;
		policy = policyDao.getUser(policyId);
		policy.printPolicyDetails();
	}	
//----------------------------------------------------------------------------------------------------//
	public static void printAllPolicy() {
		List<Policy> policy = policyDao.getAllUser();
		for (int i = 0; i < policy.size();i++) {
			policy.get(i).printPolicyDetails();
		}
	}
//----------------------------------------------------------------------------------------------------//		
	public static void updatePolicy() {
		
		Date utilDate = new Date();  // Current date
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		policy.setPolicyDetails("Policy-002", 1, 30000.0, POLICY_STATUS.ACTIVE, sqlDate);
		policy.setPolicyId(2);
		policyDao.updateUser(policy);
		System.out.println("Policy with " + policy.getPolicyId() + " Updated Successfully");
	}
//----------------------------------------------------------------------------------------------------//
	public static void deletePolicy() {
		int policyId = 2;
		policyDao.deleteUser(policyId);
		System.out.println("Policy with id: "+policyId+" Deleted Successfully");
	}
}
