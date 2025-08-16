package com.example.projectHCMS.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
 
@Entity
@Table(name = "claim")
public class Claim {
 
	public enum ClaimStatus {PENDING,APPROVED,REJECTED}
	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long claimId;

    @NotNull
    @Column(name = "policy_id", nullable = false)
    private Long policyId;

    @NotNull
    @DecimalMin(value = "1000.00", inclusive = true)
    @Column(name = "claim_amount", nullable = false)
    private BigDecimal claimAmount;
    
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "claim_date", nullable = false)
    private LocalDate claimDate;
 
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status", nullable = false)
    private ClaimStatus claimStatus;

    @NotNull
    @Column(name = "adjuster_id", nullable = false)
    private Long adjusterId;
 
// Getters and setters
    public Long getClaimId() {
        return claimId;
    }
    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }
 
    public Long getPolicyId() {
        return policyId;
    }
    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }
 
    public BigDecimal getClaimAmount() {
        return claimAmount;
    }
 
    public void setClaimAmount(BigDecimal claimAmount) {
        this.claimAmount = claimAmount;
    }
 
    public LocalDate getClaimDate() {
        return claimDate;
    }
    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }
 
    public ClaimStatus getClaimStatus() {
        return claimStatus;
    }
 
    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }
 
    public Long getAdjusterId() {
        return adjusterId;
    }
    public void setAdjusterId(Long adjusterId) {
        this.adjusterId = adjusterId;
    }
    
    
//Constructors
	public Claim(Long claimId, Long policyId, BigDecimal claimAmount, LocalDate claimDate, ClaimStatus claimStatus,
			Long adjusterId) {
		super();
		this.claimId = claimId;
		this.policyId = policyId;
		this.claimAmount = claimAmount;
		this.claimDate = claimDate;
		this.claimStatus = claimStatus;
		this.adjusterId = adjusterId;
	}
	
	public Claim() {
		
	}

}

 