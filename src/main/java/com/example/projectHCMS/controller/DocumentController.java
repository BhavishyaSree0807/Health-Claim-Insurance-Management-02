package com.example.projectHCMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

import com.example.projectHCMS.Entities.Document;
import com.example.projectHCMS.Entities.User;
import com.example.projectHCMS.serviceImpl.DocumentServiceImpl;

import java.io.IOException;
import java.util.Optional;


@Controller
@RequestMapping("/documents")
public class DocumentController {
	
	@Autowired
	private DocumentServiceImpl docserimpl;

//--------------------------------------UPLOAD DOCUMENT-----------------------------------------------------//
	    @GetMapping("/upload")
	    public String showUploadForm(Model model) {
	        model.addAttribute("document", new Document());
	        return "uploadDocument"; // View name (e.g., uploadDocument.html or .jsp)
	    }

	    @PostMapping("/upload")
	    public String uploadDocument(@ModelAttribute Document document,
	                                 @RequestParam("file") MultipartFile file,
	                                 Model model) throws IOException {
	        if (file != null && !file.isEmpty()) {
	            document.setFileData(file.getBytes());
	            document.setDocumentPath(file.getOriginalFilename());
	        }
        // default newly uploaded documents to PENDING for review flow
        if (document.getVerificationStatus() == null) {
        	document.setVerificationStatus(Document.VerificationStatus.PENDING);
        }
	        Document saved = docserimpl.addDocument(document);
	        model.addAttribute("message", "Document uploaded successfully with ID: " + saved.getDocumentId());
	        return "uploadSuccess"; // View name
	    }

//--------------------------------------LIST ALL DOCUMENTS-----------------------------------------------------//
	    @GetMapping("/all")
	    public String listAllDocuments(Model model, jakarta.servlet.http.HttpSession session) {
	    	User current = (User) session.getAttribute("currentSession");
	    	model.addAttribute("documents", current != null ? docserimpl.getAllDocumentsForUser(current.getUserId()) : java.util.List.of());
	    	return "documents";
	    }

//--------------------------------------STREAM FILE INLINE-----------------------------------------------------//
	    @GetMapping("/{documentId}/file")
	    public ResponseEntity<byte[]> viewDocumentFile(@PathVariable Long documentId) {
	    	Optional<Document> documentOpt = docserimpl.getDocumentDetails(documentId);
	    	if (documentOpt.isEmpty() || documentOpt.get().getFileData() == null) {
	    		return ResponseEntity.notFound().build();
	    	}
	    	Document doc = documentOpt.get();
	    	MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
	    	if (doc.getDocumentType() != null) {
	    		switch (doc.getDocumentType().name()) {
	    			case "PDF": mediaType = MediaType.APPLICATION_PDF; break;
	    			case "JPG": mediaType = MediaType.IMAGE_JPEG; break;
	    			case "PNG": mediaType = MediaType.IMAGE_PNG; break;
	    			case "DOC": mediaType = MediaType.parseMediaType("application/msword"); break;
	    			default: mediaType = MediaType.APPLICATION_OCTET_STREAM;
	    		}
	    	}
	    	String filename = doc.getDocumentPath() != null ? doc.getDocumentPath() : ("document-" + documentId);
	    	return ResponseEntity.ok()
	    			.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
	    			.contentType(mediaType)
	    			.body(doc.getFileData());
	    }
 
//--------------------------------------GET DOCUMENT DETAILS-----------------------------------------------------// 
	    @GetMapping("/{documentId}")
	    public String getDocumentDetails(@PathVariable Long documentId, Model model) {
	        Optional<Document> documentOpt = docserimpl.getDocumentDetails(documentId);
	        if (documentOpt.isPresent()) {
	            model.addAttribute("document", documentOpt.get());
	            return "documentDetails"; // View name
	        } else {
	            model.addAttribute("error", "Document not found");
	            return "errorPage"; // View name
	        }
	    }

//--------------------------------------DELETE DOCUMENT-----------------------------------------------------//    
	    @DeleteMapping("/delete/{documentId}")
	    public String deleteDocument(@PathVariable Long documentId, Model model) {
	        boolean deleted = docserimpl.removeDocument(documentId);
	        if (deleted == true) {
	            model.addAttribute("message", "Document deleted successfully");
	        } else {
	            model.addAttribute("error", "Document not found or could not be deleted");
	        }
	        return "deleteResult"; // View name
	    }
//------------------------------------------------------------------------------------------------------//	
}

