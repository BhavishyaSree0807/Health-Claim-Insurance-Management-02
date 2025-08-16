package com.example.projectHCMS.Services;

import java.util.Optional;
import java.util.List;

import com.example.projectHCMS.Entities.Document;


public interface DocumentService {
	
	Document addDocument(Document document);
	Optional<Document> getDocumentDetails(Long documentId);
	Boolean removeDocument(Long documentId);
	List<Document> getAllDocuments();
	List<Document> getAllDocumentsForUser(Long userId);
	Document updateVerificationStatus(Long documentId, Document.VerificationStatus status);
	List<Document> getDocumentsByVerificationStatus(Document.VerificationStatus status);

}