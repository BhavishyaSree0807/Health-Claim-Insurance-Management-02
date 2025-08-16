package com.example.dao;

import java.util.List;

import com.example.model.Policy;

public interface PolicyDao {
	Policy getUser(int Pid);
	List<Policy> getAllUser();
	void addUser(Policy user);
	void updateUser(Policy user);
	void deleteUser(int Pid);

}
