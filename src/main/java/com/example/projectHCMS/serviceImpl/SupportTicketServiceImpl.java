package com.example.projectHCMS.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectHCMS.Entities.SupportTicket;
import com.example.projectHCMS.Repositories.SupportTicketRepository;
import com.example.projectHCMS.Services.SupportTicketService;

@Service
public class SupportTicketServiceImpl implements SupportTicketService {

	@Autowired
	private SupportTicketRepository strepo;
	
//------------------------------------------------------------------------------------------------------//
	@Override
	public SupportTicket addTicket(SupportTicket supportTicket) {
		SupportTicket ticket = strepo.save(supportTicket);
		System.out.println("Ticket Created Successfully");
		return ticket;
	}
//------------------------------------------------------------------------------------------------------//
	@Override
	public SupportTicket getTicket(Long ticketId) {
		SupportTicket ticket = strepo.findByTicketId(ticketId);
		return ticket;
	}
//------------------------------------------------------------------------------------------------------//
	@Override
	public SupportTicket updateTicket(Long ticketId, SupportTicket supportTicket) {
		SupportTicket existingTicket = strepo.findByTicketId(ticketId);

		if (existingTicket == null) {
			return null;
		} else {

			if (supportTicket.getTicketStatus() != null) {
				existingTicket.setTicketStatus(supportTicket.getTicketStatus());
			}

			if (supportTicket.getIssueDescription() != null) {
				existingTicket.setIssueDescription(supportTicket.getIssueDescription());;
			}

			return strepo.save(existingTicket);
		}
	}
//------------------------------------------------------------------------------------------------------//
	@Override
	public Boolean deleteTicket(Long ticketId) {
		if (strepo.existsById(ticketId)) {
			strepo.deleteById(ticketId);
			return true;
		} else
			return false;
	}
//------------------------------------------------------------------------------------------------------//
	@Override
	public List<SupportTicket> getAllTickets(Long userID) {
		return strepo.findAllByUserId(userID);
	}
//------------------------------------------------------------------------------------------------------//
	@Override
	public List<SupportTicket> getAllTickets() {
	    return strepo.findAll();
	}

}

