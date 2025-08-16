package com.example.projectHCMS.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projectHCMS.Entities.Policy;
import com.example.projectHCMS.Entities.User;
import com.example.projectHCMS.serviceImpl.PolicyServiceImpl;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/policy")
public class PolicyController {

	@Autowired
	private PolicyServiceImpl psImpl;

	//--------------------------------------------CREATE POLICY-------------------------------------------------------------//
	@GetMapping("/create")
	public String showCreatePolicyForm(Model model) {
		Policy policy = new Policy();
		model.addAttribute("policy", policy);

		List<Policy.PolicyStatus> policyStatusList = Arrays.asList(Policy.PolicyStatus.values());
		model.addAttribute("policyStatusList", policyStatusList);

		return "createPolicy";
	}

	@PostMapping("/create")
	public String createPolicy(@Valid @ModelAttribute("policy") Policy policy,
	                          BindingResult bindingResult,
	                          Model model,
	                          RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			List<Policy.PolicyStatus> policyStatusList = Arrays.asList(Policy.PolicyStatus.values());
			model.addAttribute("policyStatusList", policyStatusList);
			return "createPolicy";
		}
		try {
			Policy savedPolicy = psImpl.createPolicy(policy);
			redirectAttributes.addFlashAttribute("successMessage",
					"Policy created successfully with ID: " + savedPolicy.getPolicyId());
			return "redirect:/policy/all";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error creating policy: " + e.getMessage());
			return "redirect:/policy/create";
		}
	}
	//--------------------------------------------GET ALL POLICIES-------------------------------------------------------------//
	@GetMapping("/all")
    public String getAllPolicies(Model model, HttpSession session) {
        try {
        	User current = (User) session.getAttribute("currentSession");
        	List<Policy> policies;
        	if (current != null && current.getRole() == User.ROLE.AGENT) {
        		policies = psImpl.getAllPolicies();
        	} else if (current != null) {
        		policies = psImpl.getPoliciesForUser(current.getUserId());
        	} else {
        		policies = psImpl.getAllPolicies();
        	}
            model.addAttribute("policies", policies);
            return "myPolicies";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error fetching policies: " + e.getMessage());
            return "error";
        }
    }
	
	//--------------------------------------------GET POLICY DETAILS-------------------------------------------------------------//
	@GetMapping("/get/{id}")
    public String getPolicyById(@PathVariable("id") Long policyId, Model model) {
        try {
            Policy policy = psImpl.getPolicyDetails(policyId);
            model.addAttribute("policy", policy);
            return "policyDetails";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Policy not found with ID: " + policyId);
            return "error";
        }
    }
	//--------------------------------------------UPDATE POLICY-------------------------------------------------------------//
	@GetMapping("/edit/{id}")
    public String showUpdatePolicyForm(@PathVariable("id") Long policyId, Model model) {
        try {
            Policy policy = psImpl.getPolicyDetails(policyId);
            model.addAttribute("policy", policy);
            
            List<Policy.PolicyStatus> policyStatusList = Arrays.asList(Policy.PolicyStatus.values());
            model.addAttribute("policyStatusList", policyStatusList);
            
            return "updatePolicy"; // You'll need to create this template
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Policy not found with ID: " + policyId);
            return "error";
        }
    }
 
    /**
     * Update policy
     */
    @PostMapping("/update/{id}")
    public String updatePolicy(@PathVariable("id") Long policyId,
                             @ModelAttribute("policy") Policy policy,
                             RedirectAttributes redirectAttributes) {
        try {
            Policy updatedPolicy = psImpl.updatePolicy(policyId, policy);
            redirectAttributes.addFlashAttribute("successMessage",
                "Policy updated successfully");
            return "redirect:/policy/all";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Error updating policy: " + e.getMessage());
            return "redirect:/policy/edit/" + policyId;
        }
    }
	//--------------------------------------------DELETE POLICY-------------------------------------------------------------//
    @PostMapping("/delete/{id}")
    public String deletePolicy(@PathVariable("id") Long policyId,
                             RedirectAttributes redirectAttributes) {
        try {
            psImpl.deletePolicy(policyId);
            redirectAttributes.addFlashAttribute("successMessage",
                "Policy deleted successfully");
            return "redirect:/policy/all";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                "Error deleting policy: " + e.getMessage());
            return "redirect:/policy/all";
        }
    }
//---------------------------------------------------------------------------------------------------------------------//
    @GetMapping("/downloads")
	public String showPolicyDownloads(Model model) {
		List<Map<String, String>> downloads = Arrays.asList(
			Map.of("name", "Health Shield Basic", "file", "/health-shield-basic.txt"),
			Map.of("name", "Health Shield Plus", "file", "/health-shield-plus.txt"),
			Map.of("name", "Family Care Plan", "file", "/family-care-plan.txt"),
			Map.of("name", "Senior Secure", "file", "/senior-secure.txt"),
			Map.of("name", "Critical Care Cover", "file", "/critical-care-cover.txt")
		);
		model.addAttribute("downloads", downloads);
		return "policyDownloads"; // templates/policyDownloads.html
	}

    @GetMapping("/agentdownloads")
	public String showPolicyDownloads1(Model model) {
		List<Map<String, String>> downloads = Arrays.asList(
			Map.of("name", "Health Shield Basic", "file", "/health-shield-basic.txt"),
			Map.of("name", "Health Shield Plus", "file", "/health-shield-plus.txt"),
			Map.of("name", "Family Care Plan", "file", "/family-care-plan.txt"),
			Map.of("name", "Senior Secure", "file", "/senior-secure.txt"),
			Map.of("name", "Critical Care Cover", "file", "/critical-care-cover.txt")
		);
		model.addAttribute("downloads", downloads);
		return "policyDownloads1"; // templates/policyDownloads.html
	}

//--------------------------------------------------------------------------------------------------------------------//
}
