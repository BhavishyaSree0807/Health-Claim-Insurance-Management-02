package com.example.model;

public class Document {

	public enum Document_Type {
		PDF, JPG, PNG, DOC
	}

	private int documentId; // primary key
	private int claimId; // foreign key
	private String documentName;
	private String documentPath;
	private Document_Type documentType;

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public int getClaimId() {
		return claimId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public Document_Type getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Document_Type documentType) {
		this.documentType = documentType;
	}

	public void printDocumentDetails() {
		System.out.println(getDocumentId() + " " + getClaimId() + " " + getDocumentName() + " " + getDocumentPath()
				+ " " + getDocumentType());
	}
	
	public void setDocumentDetails(int claimId, String documentName, String documentPath, Document_Type documentType) {
		
		setClaimId(claimId);
		setDocumentName(documentName);
		setDocumentPath(documentPath);
		setDocumentType(documentType);
	}

}
