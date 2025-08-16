package com.example.projectHCMS.Repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projectHCMS.Entities.Document;


@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
	
	Optional<Document> findByDocumentId(Long documentId);

	@Query("select d from Document d, Claim c where d.claimId = c.claimId and c.adjusterId = :userId")
	List<Document> findAllByUserId(@Param("userId") Long userId);

	List<Document> findByVerificationStatus(Document.VerificationStatus status);

}