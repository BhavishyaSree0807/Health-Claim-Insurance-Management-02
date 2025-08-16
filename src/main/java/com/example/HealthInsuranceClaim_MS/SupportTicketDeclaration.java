package com.example.HealthInsuranceClaim_MS;

import java.util.Date;
import java.util.List;

import com.example.dao.SupportTicketDao;
import com.example.dao.impl.SupportTicketDaoImpl;
import com.example.model.SupportTicket;
import com.example.model.SupportTicket.TICKET_STATUS;

public class SupportTicketDeclaration {
	public static SupportTicketDao supportticketdao = new SupportTicketDaoImpl();
	public static SupportTicket supportticket = new SupportTicket();

	public static void createTicket() {

		Date utilDate = new Date(); // Current date
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

		supportticket.setTicketDetails(3, "Delay in issuing claim amount", TICKET_STATUS.OPEN, sqlDate);
		supportticketdao.addUser(supportticket);

		System.out.println("Ticket created successfully");
	}
//--------------------------------------------------------------------------------------------------------//

	public static void printTicket() {

		int ticketId = 3;
		supportticket = supportticketdao.getuesr(ticketId);
		supportticket.printTicketDetails();

	}
//--------------------------------------------------------------------------------------------------------//

	public static void printAllTickets() {

		List<SupportTicket> supporttickect = supportticketdao.getAllUser();
		for (int i = 0; i < supporttickect.size(); i++) {
			supporttickect.get(i).printTicketDetails();
		}
	}
//--------------------------------------------------------------------------------------------------------//

	public static void updateTicketDetails() {

		Date utilDate = new Date(); // Current date
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

		supportticket.setTicketDetails(3, "Delay in issuing claim amount", TICKET_STATUS.RESOLVED, sqlDate);
		supportticket.setTicketId(3);
		supportticketdao.updateUser(supportticket);

		System.out.println("Ticket with ticketId: " + supportticket.getTicketId() + " has been Updated sucessfully.");
	}
//-----------------------------------------------------------------------------------------------------------//
	
	public static void cancelTicket() {
		
		int ticketId=3;
		supportticketdao.deleteUser(ticketId);
		
		System.out.println("Tickect wit ticketId: " + ticketId + "has been cancelled.");
	}
}
	