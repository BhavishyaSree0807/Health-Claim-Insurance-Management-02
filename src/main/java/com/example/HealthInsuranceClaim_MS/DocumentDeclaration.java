package com.example.HealthInsuranceClaim_MS;

import com.example.dao.impl.DocumentDaoImpl;

import java.util.Date;
import java.util.List;

import com.example.dao.*;
import com.example.model.Document;
import com.example.model.Document.Document_Type;

public class DocumentDeclaration {
	
	public static Document document = new Document();
	public static DocumentDao documentdao = new DocumentDaoImpl();
	
	public static void addDocument() {
		
		document.setDocumentDetails(1, "Pancard", "onedrive/karthik/documents/pancard", Document_Type.PDF );
		documentdao.addUser(document);
		
		System.out.println("Document added sucessfully");
	}
//-----------------------------------------------------------------------------------------------------------------//	
	public static void getdocument() {
		
		int documentId = 1;
		document = documentdao.getuesr(documentId);
		document.printDocumentDetails();
	}
//-----------------------------------------------------------------------------------------------------------------//
	public static void getAllDocuments() {
		
		List<Document> document = documentdao.getAllUser();
		for (int i=0 ; i<document.size(); i++) {
			document.get(i).printDocumentDetails();}
		}
//-----------------------------------------------------------------------------------------------------------------//	
	public static void updateDocument() {
		
		document.setDocumentDetails(1, "AadharCard", "onedrive/karthik/documents/aadharcard", Document_Type.PDF);
		document.setDocumentId(1);
		documentdao.updateUser(document);
		
		System.out.println("Document with documentId: " + document.getDocumentId() + " has been updated sucessfully.");
		
	}
//-------------------------------------------------------------------------------------------------------------------//	
	public static void removeDocument() {
		
		int documentId=1;
		documentdao.deleteUser(documentId);
		
		System.out.println("Document with documentId: " + documentId + " has been deleted" );
	}
	
}
