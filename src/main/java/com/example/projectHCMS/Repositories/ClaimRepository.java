package com.example.projectHCMS.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectHCMS.Entities.Claim;
import com.example.projectHCMS.Entities.Claim.ClaimStatus;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

   List<Claim> findByPolicyId(Long policyId);
   List<Claim> findByClaimStatus(ClaimStatus status);

}

