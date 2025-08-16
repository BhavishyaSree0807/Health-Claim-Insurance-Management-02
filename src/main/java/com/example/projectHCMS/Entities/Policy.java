package com.example.projectHCMS.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id")
    private Long policyId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "policy_number", nullable = false, unique = true, length = 50)
    private String policyNumber;

    @NotNull
    @Column(name = "policyholder_id", nullable = false)
    private Long policyholderId;

    @NotNull
    @DecimalMin(value = "1000.00", inclusive = true)
    @Column(name = "coverage_amount", nullable = false)
    private BigDecimal coverageAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status", nullable = false)
    private PolicyStatus policyStatus;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDate createdDate;
    

// Getters and Setters
    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Long getPolicyholderId() {
        return policyholderId;
    }

    public void setPolicyholderId(Long policyholderId) {
        this.policyholderId = policyholderId;
    }

    public BigDecimal getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(BigDecimal coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public enum PolicyStatus {
        ACTIVE, INACTIVE, CANCELLED
    }

//Constructors
	public Policy(Long policyId, @NotBlank @Size(max = 50) String policyNumber, @NotNull Long policyholderId,
			@NotNull @DecimalMin(value = "1000.00", inclusive = true) BigDecimal coverageAmount,
			@NotNull PolicyStatus policyStatus, LocalDate createdDate) {
		this.policyId = policyId;
		this.policyNumber = policyNumber;
		this.policyholderId = policyholderId;
		this.coverageAmount = coverageAmount;
		this.policyStatus = policyStatus;
		this.createdDate = createdDate;
	}

	public Policy() {
		// TODO Auto-generated constructor stub
	}

//To String
	@Override
	public String toString() {
		return "Policy [policyId=" + policyId + ", policyNumber=" + policyNumber + ", policyholderId=" + policyholderId
				+ ", coverageAmount=" + coverageAmount + ", policyStatus=" + policyStatus + ", createdDate="
				+ createdDate + "]";
	}
    
	
    
}
