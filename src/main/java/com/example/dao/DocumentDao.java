package com.example.dao;

import java.util.List;

import com.example.model.Document;

public interface DocumentDao {
	Document getuesr(int docid);
	List<Document> getAllUser();
	void addUser(Document user);
	void updateUser(Document user);
	void deleteUser(int docid);

}
