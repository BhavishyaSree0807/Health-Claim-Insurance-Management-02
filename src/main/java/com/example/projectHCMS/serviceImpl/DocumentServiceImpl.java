package com.example.projectHCMS.serviceImpl;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectHCMS.Entities.Document;
import com.example.projectHCMS.Repositories.DocumentRepository;
import com.example.projectHCMS.Services.DocumentService;


@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository docrepo;

	@Override
	public Document addDocument(Document document) {
		Document newdoc = docrepo.save(document);
		System.out.println("Document added successfully!!");
		return newdoc;
	}

	@Override
	public Optional<Document> getDocumentDetails(Long documentId) {
		Optional<Document> doc = docrepo.findByDocumentId(documentId);
		if (doc == null) {
			return Optional.empty();
		} else
			return doc;
	}

	@Override
	public Boolean removeDocument(Long documentId) {
		if (docrepo.existsById(documentId)) {
			docrepo.deleteById(documentId);
			return true;
		} else
			return false;

	}

	@Override
	public List<Document> getAllDocuments() {
		return docrepo.findAll();
	}

	@Override
	public List<Document> getAllDocumentsForUser(Long userId) {
		return docrepo.findAllByUserId(userId);
	}
	
	@Override
	public Document updateVerificationStatus(Long documentId, Document.VerificationStatus status) {
		Optional<Document> docOpt = docrepo.findByDocumentId(documentId);
		if (docOpt.isEmpty()) return null;
		Document d = docOpt.get();
		d.setVerificationStatus(status);
		return docrepo.save(d);
	}

	@Override
	public List<Document> getDocumentsByVerificationStatus(Document.VerificationStatus status) {
		return docrepo.findByVerificationStatus(status);
	}

}

