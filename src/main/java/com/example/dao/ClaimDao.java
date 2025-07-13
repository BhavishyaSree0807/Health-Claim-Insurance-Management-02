package com.example.dao;

import java.util.List;

import com.example.model.Claim;

public interface ClaimDao {
	Claim getuser(int claimid);
	List<Claim> getAllUser();
	void addUser(Claim user);
	void updateUser(Claim user);
	void deleteUser(int claimid);


}
