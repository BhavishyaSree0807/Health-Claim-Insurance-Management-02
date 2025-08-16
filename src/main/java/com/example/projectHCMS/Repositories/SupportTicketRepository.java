package com.example.projectHCMS.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectHCMS.Entities.SupportTicket;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long>{
	
	SupportTicket findByTicketId(Long ticketId);
	List<SupportTicket> findAllByUserId(Long userId);

}
