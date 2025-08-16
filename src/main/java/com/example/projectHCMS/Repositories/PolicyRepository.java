//package com.example.HCMS.Repository;
//
//import com.example.HCMS.entity.Policy;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface PolicyRepository extends JpaRepository<Policy, Integer> {
//    
//    // Find policies by policyholder ID
//    List<Policy> findByPolicyholderId(int policyholderId);
//    
//    // Find policies by policy status
//    List<Policy> findByPolicyStatus(Policy.PolicyStatus policyStatus);
//    
//    // Find policies by policyholder ID and status
//    List<Policy> findByPolicyholderIdAndPolicyStatus(int policyholderId, Policy.PolicyStatus policyStatus);
//    
//    // Custom query to find policies by policyholder ID ordered by created date
//    @Query("SELECT p FROM Policy p WHERE p.policyholderId = :policyholderId ORDER BY p.createdDate DESC")
//    List<Policy> findByPolicyholderIdOrderByCreatedDateDesc(@Param("policyholderId") int policyholderId);
//    
//    // Find policy by policy number (useful for search)
//    Policy findByPolicyNumber(String policyNumber);
//    
//    // Check if policy exists by policy number
//    boolean existsByPolicyNumber(String policyNumber);
//} 


//---------------------------------------------------------
package com.example.projectHCMS.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projectHCMS.Entities.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long>{
	
	Optional<Policy> findByPolicyId(Long policyId);
	List<Policy> findByPolicyholderId(Long policyholderId);
}
