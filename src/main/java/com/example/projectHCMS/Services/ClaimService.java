package com.example.projectHCMS.Services;

import java.util.List;

import com.example.projectHCMS.Entities.Claim;
import com.example.projectHCMS.Entities.Claim.ClaimStatus;

public interface ClaimService {

   Claim submitClaim(Claim claim);
   Claim getClaimDetails(Long claimId);
   Claim updateClaimStatus(Long claimId, ClaimStatus status);
   List<Claim> getAllClaimsByPolicy(Long policyId);
   List<Claim> getClaimsByStatus(ClaimStatus status);

}


