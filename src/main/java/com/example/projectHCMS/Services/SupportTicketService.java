package com.example.projectHCMS.Services;
import java.util.List;

import com.example.projectHCMS.Entities.SupportTicket;

public interface SupportTicketService {
	
	SupportTicket addTicket(SupportTicket supportTicket);
	SupportTicket getTicket(Long ticketId);
	SupportTicket updateTicket(Long ticketId, SupportTicket supportTicket);
	Boolean deleteTicket(Long ticketId);
	List<SupportTicket> getAllTickets(Long userID);
	List<SupportTicket> getAllTickets();
}