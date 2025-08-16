package com.example.projectHCMS.serviceImpl;

import com.example.projectHCMS.Entities.Claim;
import com.example.projectHCMS.Entities.Claim.ClaimStatus;
import com.example.projectHCMS.Repositories.ClaimRepository;
import com.example.projectHCMS.Services.ClaimService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import java.time.LocalDate;

import java.util.List;
 
@Service
public class ClaimServiceImpl implements ClaimService {
 
	@Autowired
    private ClaimRepository claimRepository;
 
	public class ClaimNotFoundException extends RuntimeException {
	    public ClaimNotFoundException(String message) {
	        super(message);
	    }
	}
	
    @Override
    public Claim submitClaim(Claim claim) {
    	if (claim.getPolicyId() == null || claim.getClaimAmount() == null) {
    	    throw new IllegalArgumentException("Policy ID and Claim Amount are required");
    	}

        if (claim.getClaimDate() == null) {
        claim.setClaimDate(LocalDate.now());
        }
        claim.setClaimStatus(ClaimStatus.PENDING);
        return claimRepository.save(claim);
    }
 
    @Override
    public Claim getClaimDetails(Long claimId) {
        return claimRepository.findById(claimId)
        		.orElseThrow(() -> new ClaimNotFoundException("Claim not found with ID: " + claimId));
    }
 
    @Override
    public Claim updateClaimStatus(Long claimId, ClaimStatus status) {
        Claim claim = getClaimDetails(claimId);
        claim.setClaimStatus(status);
        return claimRepository.save(claim);
    }
 
    @Override
    public List<Claim> getAllClaimsByPolicy(Long policyId) {
        return claimRepository.findByPolicyId(policyId);
    }

	@Override
	public List<Claim> getClaimsByStatus(ClaimStatus status) {
		return claimRepository.findByClaimStatus(status);
    }

}
