package com.example.projectHCMS.Entities;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "Support_Tickets")
public class SupportTicket {
	
	private enum TICKET_STATUS{OPEN,INPROGRESS,RESOLVED}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Ticket_ID")
	private Long ticketId;
	
	@Column(name = "User_ID")
	private Long userId;
	
	@NotNull
	@Size(min = 10, max = 500)
	@Column(name = "Issue_Description")
	private String issueDescription;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Ticket_Status")
	private TICKET_STATUS ticketStatus;
	

	@CreationTimestamp
	@Column(name = "Created_Date", updatable = false)
	private LocalDate createdDate;

	//Getter and Setters
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	
	public TICKET_STATUS getTicketStatus() {
		return ticketStatus;
	}
	public void setTicketStatus(TICKET_STATUS ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	
//Constructors
	public SupportTicket(Long ticketId, Long userId, String issueDescription, TICKET_STATUS ticketStatus,
			LocalDate createdDate) {
		this.ticketId = ticketId;
		this.userId = userId;
		this.issueDescription = issueDescription;
		this.ticketStatus = ticketStatus;
		this.createdDate = createdDate;
	}

	public SupportTicket() {
		this.ticketStatus = TICKET_STATUS.OPEN;
	}

//To String
	@Override
	public String toString() {
		return "SupportTicket [ticketId=" + ticketId + ", userId=" + userId + ", issueDescription=" + issueDescription
				+ ", ticketStatus=" + ticketStatus + ", createdDate=" + createdDate + "]";
	}

}