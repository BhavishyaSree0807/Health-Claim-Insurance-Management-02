package com.example.projectHCMS.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projectHCMS.Entities.Policy;
import com.example.projectHCMS.Repositories.PolicyRepository;
import com.example.projectHCMS.Services.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {
	
	@Autowired
	private PolicyRepository policyRepo;

	@Override
	public Policy createPolicy(Policy policy) {
		if (policy.getCreatedDate() == null) {
            policy.setCreatedDate(LocalDate.now()); // set creation date when creating
        }
        return policyRepo.save(policy);
	}

	@Override
	public Policy updatePolicy(Long policyId, Policy policyData) {
		Optional<Policy> existingPolicyOpt = policyRepo.findByPolicyId(policyId);
        if (existingPolicyOpt.isPresent()) {
            Policy existingPolicy = existingPolicyOpt.get();
            existingPolicy.setPolicyNumber(policyData.getPolicyNumber());
            existingPolicy.setCoverageAmount(policyData.getCoverageAmount());
            existingPolicy.setPolicyStatus(policyData.getPolicyStatus());
            
            return policyRepo.save(existingPolicy);
        } else {
            throw new IllegalArgumentException("Policy not found");
        }
	}

	@Override
	public Policy getPolicyDetails(Long policyId) {
		 return policyRepo.findByPolicyId(policyId).orElseThrow();
	}

	@Override
	public Optional<Policy> getPolicyById(Long policyId) {
		 return policyRepo.findByPolicyId(policyId);
	}

	@Override
	public List<Policy> getAllPolicies() {
		 return policyRepo.findAll();
	}

	@Override
	public void deletePolicy(Long policyId) {
		if (policyRepo.existsById(policyId)) {
			policyRepo.deleteById(policyId);
		} else {
			throw new IllegalArgumentException("Policy not found");
		}
	}
	
	public List<Policy> getPoliciesForUser(Long userId) {
		return policyRepo.findByPolicyholderId(userId);
	}
}
