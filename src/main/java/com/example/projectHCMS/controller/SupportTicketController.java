package com.example.projectHCMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectHCMS.Entities.SupportTicket;
import com.example.projectHCMS.Entities.User;
import com.example.projectHCMS.serviceImpl.SupportTicketServiceImpl;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/supportticket")
public class SupportTicketController {
	
	@Autowired
	private SupportTicketServiceImpl stserviceImpl;
	
	//--------------------------------------GET ALL TICKETS BY USER-----------------------------------------------------//
	@GetMapping("/allTickets/User/{UserId}")
	public String getAllTicketsByUser(@PathVariable Long UserId, Model model)
	{
	    model.addAttribute("Alltickets", stserviceImpl.getAllTickets(UserId));
	    return "tickets";
	}
	//-----------------------------------------CREATE NEW TICKET--------------------------------------------------------//
	@GetMapping("/ticket/new")
	public String createTicket(Model model)
	{
		SupportTicket ticket = new SupportTicket();
		model.addAttribute("newticket", ticket);
		return "create_ticket";
	}
	
	@PostMapping("/tickets")
	public String saveTicket(@Valid @ModelAttribute("newticket") SupportTicket ticket,
	                        BindingResult bindingResult,
	                        Model model,
	                        HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "create_ticket";
		}
		User current = (User) session.getAttribute("currentSession");
		if (current != null) {
			ticket.setUserId(current.getUserId());
		}
		stserviceImpl.addTicket(ticket);
		model.addAttribute("successMessage", "Your request is submitted");
		model.addAttribute("newticket", new SupportTicket());
		return "create_ticket";
	}
	//-------------------------------------------UPDATE TICKET-----------------------------------------------------//
	@GetMapping("/editticket/{ticketId}")
	public String editTicket(@PathVariable Long ticketId, Model model) {
		model.addAttribute("ticket", stserviceImpl.getTicket(ticketId));
		return "edit_ticket";
	}
 
	@PostMapping("/ticket/{ticketId}")
	public String updateTicket(@PathVariable Long ticketId,
			@ModelAttribute("ticket") SupportTicket updatedTicket,
			Model model)
	{
		stserviceImpl.updateTicket(ticketId, updatedTicket);
		return "redirect:/supportticket/allTickets/User/" + updatedTicket.getUserId();
	}
	//---------------------------------------GET TICKET DETAILS-----------------------------------------------------//
	@GetMapping("/allTickets/{ticketId}")
	public String getTicketById( @PathVariable Long ticketId, Model model)
	{
	    SupportTicket ticket = stserviceImpl.getTicket(ticketId);
	    if (ticket != null) {
	        model.addAttribute("ticket", ticket);
	    } else {
	        model.addAttribute("error", "ticket not found with ID: " +ticketId);
	    }
	    return "ticket-details";
	}
//------------------------------------------------------------------------------------------------------//
	@GetMapping("/tickets")
	public String getAllTickets(Model model, HttpSession session) {
	    User current = (User) session.getAttribute("currentSession");
	    List<SupportTicket> allTickets = current != null ? stserviceImpl.getAllTickets(current.getUserId()) : List.of();
	    model.addAttribute("Alltickets", allTickets);
	    return "Alltickets";
	}

}
