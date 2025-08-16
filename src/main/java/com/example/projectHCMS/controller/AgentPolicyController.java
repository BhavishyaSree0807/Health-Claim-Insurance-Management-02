package com.example.projectHCMS.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectHCMS.Entities.Policy;
import com.example.projectHCMS.serviceImpl.PolicyServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/agent/policies")
public class AgentPolicyController {

	@Autowired
	private PolicyServiceImpl policyService;

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("policy", new Policy());
		List<Policy.PolicyStatus> policyStatusList = Arrays.asList(Policy.PolicyStatus.values());
		model.addAttribute("policyStatusList", policyStatusList);
		return "agentCreatePolicy";
	}

	@PostMapping("/create")
	public String createPolicy(@Valid @ModelAttribute("policy") Policy policy,
	                          BindingResult bindingResult,
	                          Model model) {
		List<Policy.PolicyStatus> policyStatusList = Arrays.asList(Policy.PolicyStatus.values());
		model.addAttribute("policyStatusList", policyStatusList);
		if (bindingResult.hasErrors()) {
			return "agentCreatePolicy";
		}
		Policy saved = policyService.createPolicy(policy);
		model.addAttribute("successMessage", "Policy created successfully with ID: " + saved.getPolicyId());
		model.addAttribute("policy", new Policy());
		return "agentCreatePolicy";
	}
} 