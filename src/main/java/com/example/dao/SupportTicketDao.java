package com.example.dao;

import java.util.List;

import com.example.model.SupportTicket;

public interface SupportTicketDao {
	SupportTicket getuesr(int STid);
	List<SupportTicket> getAllUser();
	void addUser(SupportTicket user);
	void updateUser(SupportTicket user);
	void deleteUser(int STid);

}
