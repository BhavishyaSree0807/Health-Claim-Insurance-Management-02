package com.example.projectHCMS.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.projectHCMS.Entities.Claim;
import com.example.projectHCMS.Entities.Claim.ClaimStatus;
import com.example.projectHCMS.Entities.Policy;
import com.example.projectHCMS.Entities.User;
import com.example.projectHCMS.Services.ClaimService;
import com.example.projectHCMS.serviceImpl.PolicyServiceImpl;
import jakarta.servlet.http.HttpSession;
 
@Controller
@RequestMapping("/claims")
public class ClaimController {
	
	@Autowired
    private ClaimService claimService;

	@Autowired
	private PolicyServiceImpl policyService;
 
    @GetMapping("/new")
    public String showClaimForm(Model model) {
        model.addAttribute("claim", new Claim());
        return "claimForm";
    }
 
    @PostMapping
    public String submitClaim(@ModelAttribute Claim claim, Model model, HttpSession session) {
    	User current = (User) session.getAttribute("currentSession");
    	if (current == null) {
    		model.addAttribute("errorMessage", "Please log in to submit a claim.");
    		return "claimForm";
    	}

    	// Force the adjuster/user id to the logged-in user to prevent spoofing
    	claim.setAdjusterId(current.getUserId());

    	// Verify that the policy belongs to the logged-in user
    	boolean ownsPolicy = false;
    	for (Policy p : policyService.getPoliciesForUser(current.getUserId())) {
    		if (p.getPolicyId().equals(claim.getPolicyId())) { ownsPolicy = true; break; }
    	}
    	if (!ownsPolicy) {
    		model.addAttribute("errorMessage", "Selected Policy ID does not belong to the logged-in user.");
    		return "claimForm";
    	}

        Claim savedClaim = claimService.submitClaim(claim);
        model.addAttribute("claim", new Claim()); // keep form empty for next submission
        model.addAttribute("submittedClaim", savedClaim); // pass the submitted claim details
        return "claimForm";
    }
 
 
    @GetMapping("/{id}")
    public String getClaimDetails(@PathVariable Long id, Model model) {
        model.addAttribute("claim", claimService.getClaimDetails(id));
        return "claimDetails";
    }
 
    @GetMapping("/list")
    public String listClaimsByPolicy(@RequestParam(required = false) Long policyId, Model model, jakarta.servlet.http.HttpSession session) {
        if (policyId == null) {
        	model.addAttribute("claims", null);
        	return "claimList";
        }
        com.example.projectHCMS.Entities.User current = (com.example.projectHCMS.Entities.User) session.getAttribute("currentSession");
        if (current == null) {
        	model.addAttribute("claims", null);
        	model.addAttribute("errorMessage", "Please log in to view claims.");
        	return "claimList";
        }
        // Verify that the policy belongs to the current user
        boolean ownsPolicy = policyService.getPoliciesForUser(current.getUserId())
        	.stream().anyMatch(p -> p.getPolicyId().equals(policyId));
        if (!ownsPolicy) {
        	model.addAttribute("claims", java.util.List.of());
        	model.addAttribute("errorMessage", "The entered Policy ID does not belong to your account.");
        	return "claimList";
        }
        model.addAttribute("claims", claimService.getAllClaimsByPolicy(policyId));
        return "claimList";
    }
 
    @PostMapping("/{id}/status")
    public String updateClaimStatus(@PathVariable Long id, @RequestParam ClaimStatus status) {
        claimService.updateClaimStatus(id, status);
        return "redirect:/claims/" + id;
    }
}
