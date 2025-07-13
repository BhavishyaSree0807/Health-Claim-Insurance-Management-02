package com.example.model;

import java.util.Date;

public class SupportTicket {

	public enum TICKET_STATUS {
		OPEN, RESOLVED
	}

	private int ticketId; // primary key
	private int userId; // foreign key
	private String issueDescription;
	private TICKET_STATUS ticketStatus;
	private Date createdDate;

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public TICKET_STATUS getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TICKET_STATUS ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public void printTicketDetails() {
		System.out.println(getTicketId() + " " + getUserId() + " " + getIssueDescription() + " " + getTicketStatus()
				+ " " + getCreatedDate());
	}

	public void setTicketDetails(int userId, String issueDescription, TICKET_STATUS ticketStatus, Date createdDate) {

		setUserId(userId);
		setIssueDescription(issueDescription);
		setTicketStatus(ticketStatus);
		setCreatedDate(createdDate);

	}
}
