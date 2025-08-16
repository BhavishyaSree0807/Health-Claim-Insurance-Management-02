package com.example.projectHCMS.Services;

import java.util.List;
import java.util.Optional;

import com.example.projectHCMS.Entities.Policy;

public interface PolicyService {
	
	Policy createPolicy(Policy policy);
	Policy updatePolicy(Long policyId, Policy policyData);
	Policy getPolicyDetails(Long policyId);
	Optional<Policy> getPolicyById(Long policyId);
	List<Policy> getAllPolicies();
	void deletePolicy(Long policyId);

}
