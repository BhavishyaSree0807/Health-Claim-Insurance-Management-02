package com.example.projectHCMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectHCMS.Repositories.UserRepository;
import com.example.projectHCMS.Entities.User;
import com.example.projectHCMS.Entities.Policy;
import com.example.projectHCMS.Entities.Claim;
import com.example.projectHCMS.Entities.Document;
import com.example.projectHCMS.serviceImpl.PolicyServiceImpl;
import com.example.projectHCMS.Services.ClaimService;
import com.example.projectHCMS.Services.DocumentService;
import com.example.projectHCMS.Entities.SupportTicket;
import com.example.projectHCMS.serviceImpl.SupportTicketServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PolicyServiceImpl policyService;

	@Autowired
	private ClaimService claimService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private SupportTicketServiceImpl stserviceImpl;

	@GetMapping("/users/all")
	public String listAllUsers(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "adminUsers";
	}

	@GetMapping("/policies/all")
	public String listAllPolicies(Model model) {
		List<Policy> policies = policyService.getAllPolicies();
		// Build user info maps
		Map<Long, String> userNameById = new HashMap<>();
		Map<Long, String> emailById = new HashMap<>();
		for (User u : userRepository.findAll()) {
			userNameById.put(u.getUserId(), u.getUserName());
			emailById.put(u.getUserId(), u.getEmail());
		}
		// Preload all documents grouped by claimId
		Map<Long, List<Document>> docsByClaimId = documentService.getAllDocuments()
			.stream().collect(Collectors.groupingBy(Document::getClaimId));
		// Build per-policy doc name/status (first doc if many)
		Map<Long, String> docNameByPolicyId = new HashMap<>();
		Map<Long, String> docStatusByPolicyId = new HashMap<>();
		Map<Long, String> claimStatusByPolicyId = new HashMap<>();
		Map<Long, String> claimAmountByPolicyId = new HashMap<>();
		for (Policy p : policies) {
			List<Claim> claims = claimService.getAllClaimsByPolicy(p.getPolicyId());
			Document first = null;
			for (Claim c : claims) {
				List<Document> list = docsByClaimId.get(c.getClaimId());
				if (list != null && !list.isEmpty()) { first = list.get(0); }
				// keep last seen claim for status/amount; simple heuristic
				claimStatusByPolicyId.put(p.getPolicyId(), c.getClaimStatus() != null ? c.getClaimStatus().name() : "-");
				claimAmountByPolicyId.put(p.getPolicyId(), c.getClaimAmount() != null ? c.getClaimAmount().toPlainString() : "-");
			}
			if (first != null) {
				docNameByPolicyId.put(p.getPolicyId(), first.getDocumentName());
				docStatusByPolicyId.put(p.getPolicyId(), first.getVerificationStatus() != null ? first.getVerificationStatus().name() : "PENDING");
			} else {
				docNameByPolicyId.put(p.getPolicyId(), "-");
				docStatusByPolicyId.put(p.getPolicyId(), "-");
			}
			// Ensure defaults if no claims
			claimStatusByPolicyId.putIfAbsent(p.getPolicyId(), "-");
			claimAmountByPolicyId.putIfAbsent(p.getPolicyId(), "-");
		}
		model.addAttribute("policies", policies);
		model.addAttribute("userNameById", userNameById);
		model.addAttribute("emailById", emailById);
		model.addAttribute("docNameByPolicyId", docNameByPolicyId);
		model.addAttribute("docStatusByPolicyId", docStatusByPolicyId);
		model.addAttribute("claimStatusByPolicyId", claimStatusByPolicyId);
		model.addAttribute("claimAmountByPolicyId", claimAmountByPolicyId);
		return "adminPolicies";
	}

	@GetMapping("/claims/all")
	public String listAllClaims(Model model) {
		// Aggregate all claims via policies without changing repository/service API
		java.util.List<Policy> policies = policyService.getAllPolicies();
		java.util.List<Claim> allClaims = new java.util.ArrayList<>();
		for (Policy p : policies) {
			allClaims.addAll(claimService.getAllClaimsByPolicy(p.getPolicyId()));
		}
		model.addAttribute("claims", allClaims);
		return "adminClaims";
	}

	@GetMapping("/support/all-tickets")
	public String adminAllTickets(Model model) {
		java.util.List<com.example.projectHCMS.Entities.SupportTicket> tickets = stserviceImpl.getAllTickets();
		model.addAttribute("tickets", tickets);
		java.util.Map<Long, String> emailById = new java.util.HashMap<>();
		for (User u : userRepository.findAll()) {
			emailById.put(u.getUserId(), u.getEmail());
		}
		model.addAttribute("emailById", emailById);
		return "adminTickets";
	}

	@org.springframework.web.bind.annotation.PostMapping("/support/ticket/{ticketId}/status")
	public String updateTicketStatus(@org.springframework.web.bind.annotation.PathVariable Long ticketId,
			@org.springframework.web.bind.annotation.RequestParam("ticketStatus") String status) {
		SupportTicket existing = stserviceImpl.getTicket(ticketId);
		if (existing != null) {
			try {
				java.lang.reflect.Field f = SupportTicket.class.getDeclaredField("ticketStatus");
				f.setAccessible(true);
				Class<?> enumType = f.getType();
				Object enumValue = java.lang.Enum.valueOf((Class<java.lang.Enum>) enumType, status);
				f.set(existing, enumValue);
				stserviceImpl.updateTicket(ticketId, existing);
			} catch (Exception ignored) {}
		}
		return "redirect:/api/admin/support/all-tickets";
	}
} 