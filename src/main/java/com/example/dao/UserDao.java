package com.example.dao;

import java.util.List;

import com.example.model.User;

public interface UserDao {
	
	User getUser(int UId);
	List<User> getAllUser();
	void addUser(User user);
	void updateUser(User user);
	void deleteUser(int UId);
	
	
}
