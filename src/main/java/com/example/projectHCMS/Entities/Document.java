package com.example.projectHCMS.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "Documents")
public class Document {
	
	// change enum visibility so controllers and views can reference the type safely
	public enum DOCUMENT_TYPE {PDF, JPG, PNG, DOC}
	public enum VerificationStatus {PENDING, VERIFIED, REJECTED}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Document_ID")
	private Long documentId;
	
	@NotNull
	@Column(name="Claim_ID")
	private Long claimId;
	
	@NotBlank
	@Size(max = 255)
	@Column(name="Document_Name")
	private String documentName;
	
	@NotBlank
	@Size(max = 500)
	@Column(name="Document_Path")
	private String documentPath;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="Document_Type")
	private DOCUMENT_TYPE documentType;
	
	@Lob
	@Column(name = "File_Data", columnDefinition = "LONGBLOB")
	private byte[] fileData;

	@Enumerated(EnumType.STRING)
	@Column(name = "Verification_Status")
	private VerificationStatus verificationStatus;

	//Getters and Setters
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	
	public Long getClaimId() {
		return claimId;
	}
	public void setClaimId(Long claimId) {
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

	
	public DOCUMENT_TYPE getDocumentType() {
		return documentType;
	}
	public void setDocumentType(DOCUMENT_TYPE documentType) {
		this.documentType = documentType;
	}

	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public VerificationStatus getVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(VerificationStatus verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	
	//Constructors
	public Document(Long documentId, Long claimId, String documentName, String documentPath, DOCUMENT_TYPE documentType) {
		this.documentId = documentId;
		this.claimId = claimId;
		this.documentName = documentName;
		this.documentPath = documentPath;
		this.documentType = documentType;
	}

	public Document() {
		// TODO Auto-generated constructor stub
	}

	//To String
	@Override
	public String toString() {
		return "Document [documentId=" + documentId + ", claimId=" + claimId + ", documentName=" + documentName
				+ ", documentPath=" + documentPath + ", documentType=" + documentType + "]";
	}

}