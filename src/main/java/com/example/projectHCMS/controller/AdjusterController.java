package com.example.projectHCMS.controller;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectHCMS.Entities.Claim;
import com.example.projectHCMS.Entities.Claim.ClaimStatus;
import com.example.projectHCMS.Entities.Document;
import com.example.projectHCMS.Entities.Policy;
import com.example.projectHCMS.Services.ClaimService;
import com.example.projectHCMS.Services.DocumentService;
import com.example.projectHCMS.serviceImpl.DocumentServiceImpl;
import com.example.projectHCMS.serviceImpl.PolicyServiceImpl;

@Controller
@RequestMapping("/api/adjuster")
public class AdjusterController {

	@Autowired
	private ClaimService claimService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private PolicyServiceImpl policyService;

	@GetMapping("/dashboard")
	public String dashboard() {
		return "redirect:/user/adjusterDashboard";
	}

	@GetMapping("/claims/ready-for-adjustment")
	public String readyForAdjustment(Model model) {
		List<Claim> claims = claimService.getClaimsByStatus(ClaimStatus.APPROVED);
		model.addAttribute("policyNumbers", buildPolicyNumberMap(claims));
		model.addAttribute("docStatusByClaim", buildDocStatusMapForClaims(claims));
		model.addAttribute("title", "Ready for Adjustment");
		model.addAttribute("claims", claims);
		return "adjusterClaimsList";
	}

	@GetMapping("/claims/all-assigned")
	public String allAssigned(Model model) {
		List<Claim> claims = claimService.getClaimsByStatus(ClaimStatus.PENDING);
		claims.addAll(claimService.getClaimsByStatus(ClaimStatus.APPROVED));
		claims.addAll(claimService.getClaimsByStatus(ClaimStatus.REJECTED));
		model.addAttribute("policyNumbers", buildPolicyNumberMap(claims));
		model.addAttribute("docStatusByClaim", buildDocStatusMapForClaims(claims));
		model.addAttribute("title", "All Assigned Claims");
		model.addAttribute("claims", claims);
		return "adjusterClaimsList";
	}

	@GetMapping("/claims/completed")
	public String completedAdjustments(Model model) {
		List<Claim> claims = claimService.getClaimsByStatus(ClaimStatus.APPROVED);
		model.addAttribute("policyNumbers", buildPolicyNumberMap(claims));
		model.addAttribute("docStatusByClaim", buildDocStatusMapForClaims(claims));
		model.addAttribute("title", "Completed Adjustments");
		model.addAttribute("claims", claims);
		return "adjusterClaimsList";
	}

	@PostMapping("/claims/{id}/status")
	public String updateClaimStatus(@PathVariable Long id, @RequestParam ClaimStatus status,
			@RequestParam(value = "redirectTo", required = false) String redirectTo) {
		claimService.updateClaimStatus(id, status);
		return "redirect:" + (redirectTo != null ? redirectTo : "/api/adjuster/claims/all-assigned");
	}

	@GetMapping("/documents/verified")
	public String verifiedDocuments(Model model) {
		List<Document> documents = documentService.getDocumentsByVerificationStatus(Document.VerificationStatus.VERIFIED);
		model.addAttribute("title", "Verified Documents");
		model.addAttribute("documents", documents);
		model.addAttribute("policyNumbersForClaims", buildPolicyNumberMapForDocs(documents));
		return "adjusterDocuments";
	}

	@GetMapping("/documents/review")
	public String documentReview(Model model) {
		List<Document> documents = documentService.getDocumentsByVerificationStatus(Document.VerificationStatus.PENDING);
		model.addAttribute("title", "Document Review");
		model.addAttribute("documents", documents);
		model.addAttribute("policyNumbersForClaims", buildPolicyNumberMapForDocs(documents));
		return "adjusterDocuments";
	}

	@PostMapping("/documents/{id}/verify")
	public String verifyDocument(@PathVariable Long id,
			@RequestParam Document.VerificationStatus status,
			@RequestParam(value = "redirectTo", required = false) String redirectTo) {
		Document updated = documentService.updateVerificationStatus(id, status);
		if (updated != null && updated.getClaimId() != null) {
			ClaimStatus newStatus = (status == Document.VerificationStatus.VERIFIED)
				? ClaimStatus.APPROVED
				: ClaimStatus.REJECTED;
			claimService.updateClaimStatus(updated.getClaimId(), newStatus);
		}
		return "redirect:" + (redirectTo != null ? redirectTo : "/api/adjuster/documents/review");
	}

	private Map<Long, String> buildPolicyNumberMap(List<Claim> claims) {
		Map<Long, String> map = new LinkedHashMap<>();
		for (Claim c : claims) {
			Long pid = c.getPolicyId();
			if (pid != null && !map.containsKey(pid)) {
				com.example.projectHCMS.Entities.Policy p = policyService.getPolicyById(pid).orElse(null);
				map.put(pid, p != null ? p.getPolicyNumber() : "-");
			}
		}
		return map;
	}

	private Map<Long, String> buildPolicyNumberMapForDocs(List<Document> docs) {
		Map<Long, String> map = new LinkedHashMap<>();
		for (Document d : docs) {
			Long claimId = d.getClaimId();
			if (claimId == null) continue;
			Claim claim = null;
			try { claim = claimService.getClaimDetails(claimId); } catch (Exception ignored) {}
			if (claim != null) {
				Long pid = claim.getPolicyId();
				if (pid != null && !map.containsKey(claimId)) {
					com.example.projectHCMS.Entities.Policy p = policyService.getPolicyById(pid).orElse(null);
					map.put(claimId, p != null ? p.getPolicyNumber() : "-");
				}
			}
		}
		return map;
	}

	private Map<Long, String> buildDocStatusMapForClaims(List<Claim> claims) {
		Map<Long, String> map = new LinkedHashMap<>();
		// Mark VERIFIED where applicable
		for (Document d : documentService.getDocumentsByVerificationStatus(Document.VerificationStatus.VERIFIED)) {
			if (d.getClaimId() != null) map.put(d.getClaimId(), "VERIFIED");
		}
		// Mark REJECTED only if not already VERIFIED
		for (Document d : documentService.getDocumentsByVerificationStatus(Document.VerificationStatus.REJECTED)) {
			if (d.getClaimId() != null && !map.containsKey(d.getClaimId())) map.put(d.getClaimId(), "REJECTED");
		}
		// Mark PENDING (including null) if still not set
		for (Document d : documentService.getDocumentsByVerificationStatus(Document.VerificationStatus.PENDING)) {
			if (d.getClaimId() != null && !map.containsKey(d.getClaimId())) map.put(d.getClaimId(), "PENDING");
		}
		// Ensure every claim has at least a placeholder
		for (Claim c : claims) {
			map.putIfAbsent(c.getClaimId(), "-");
		}
		return map;
	}
} 