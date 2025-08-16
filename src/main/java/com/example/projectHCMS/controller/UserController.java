package com.example.projectHCMS.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projectHCMS.Entities.User;
import com.example.projectHCMS.Entities.User.ROLE;
import com.example.projectHCMS.Repositories.UserRepository;
import com.example.projectHCMS.serviceImpl.UserServiceImpl;
import com.example.projectHCMS.serviceImpl.PolicyServiceImpl;
import com.example.projectHCMS.serviceImpl.SupportTicketServiceImpl;
import com.example.projectHCMS.Repositories.ClaimRepository;

import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl usImpl;

	@Autowired
	private UserRepository usRepo;
	
	@Autowired
	private PolicyServiceImpl policyService;
	
	@Autowired
	private SupportTicketServiceImpl supportTicketService;

	@Autowired
	private ClaimRepository claimRepository;

//-----------------------------------------------REGISTER USER----------------------------------------------------//
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		
		model.addAttribute("user", new User());
		return "register"; // returns register.html
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
			@RequestParam("confirmPassword") String confirmPassword, Model model) {

		// Check for validation errors
		if (bindingResult.hasErrors()) {
			return "register";
		}

		// Check for duplicate userName
		if (usImpl.isUserNameTaken(user.getUserName())) {
			model.addAttribute("usernameError", "Username is already taken");
			return "register";
		}

		// Confirm password match check
		if (!user.getPassword().equals(confirmPassword)) {
			model.addAttribute("confirmPasswordError", "Passwords do not match");
			return "register";
		}

		usImpl.addUser(user);
		model.addAttribute("message", "User registered successfully!");
		return "redirect:/user/success"; // redirect to login page
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/success")
	public String showSuccessPage() {
		return "success"; // serves success.html
	}

//-------------------------------------------------LOGIN USER----------------------------------------------------//

	// Step 1: Show role selection page
	@GetMapping("/select-role")
	public String showRoleSelectionPage() {
		return "selectRole";
	}

	// Step 2: Redirect to role-specific login page
	@PostMapping("/select-role")
	public String handleRoleSelection(@RequestParam ROLE role) {
		
		if (role == ROLE.ADMIN)
			return "redirect:/login/admin";

		if (role == ROLE.AGENT)
			return "redirect:/login/agent";

		if (role == ROLE.CLAIM_ADJUSTER)
			return "redirect:/login/adjuster";

		if (role == ROLE.POLICYHOLDER)
			return "redirect:/login/user";

		return "redirect:/selectRole"; // fallback only if needed
	}

	// Step 3: Show login form for each role
	@GetMapping("/login/admin")
	public String showAdminLoginForm() {
		return "loginAdmin";
	}

	@GetMapping("/login/agent")
	public String showAgentLoginForm() {
		return "loginAgent";
	}

	@GetMapping("/login/adjuster")
	public String showAdjusterLoginForm() {
		return "loginAdjuster";
	}

	@GetMapping("/login/user")
	public String showUserLoginForm() {
		return "loginUser";
	}

	@GetMapping("/adjusterDashboard")
	public String adjusterDashboard() {
		return "adjusterDashboard";
	}

	@GetMapping("/adminDashboard")
	public String adminDashboard(Model model) {
		long totalUsers = usRepo.count();
		int totalPolicies = policyService.getAllPolicies() != null ? policyService.getAllPolicies().size() : 0;
		long totalClaims = claimRepository.count();
		int totalTickets = supportTicketService.getAllTickets() != null ? supportTicketService.getAllTickets().size() : 0;
		model.addAttribute("totalUsers", totalUsers);
		model.addAttribute("totalPolicies", totalPolicies);
		model.addAttribute("totalClaims", totalClaims);
		model.addAttribute("totalTickets", totalTickets);
		return "adminDashboard";
	}

	@GetMapping("/agentDashboard")
	public String agentDashboard(Model model) {
		long totalUsers = usRepo.count();
		int totalPolicies = policyService.getAllPolicies() != null ? policyService.getAllPolicies().size() : 0;
		model.addAttribute("totalUsers", totalUsers);
		model.addAttribute("totalPolicies", totalPolicies);
		List<User> policyholders = usRepo.findByRole(ROLE.POLICYHOLDER);
		model.addAttribute("policyholders", policyholders);
		return "agentDashboard";
	}

	@GetMapping("/userDashboard")
	public String userDashboard(Model model, HttpSession session) {
		User current = (User) session.getAttribute("currentSession");
		if (current != null) {
			model.addAttribute("userName", current.getUserName());
			model.addAttribute("recentPolicies", policyService.getPoliciesForUser(current.getUserId()));
			model.addAttribute("ticketCount", supportTicketService.getAllTickets(current.getUserId()) != null ? supportTicketService.getAllTickets(current.getUserId()).size() : 0);
		}
		return "userDashboard";
	}

	// Step 4: Handle login submission
	@PostMapping("/login")
	public String loginUser(@RequestParam String username, @RequestParam String password, @RequestParam ROLE role,
			Model model, HttpSession session) {

		Optional<User> userOpt = usImpl.loginUser(username, password, role);
		
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			session.setAttribute("currentSession", user);

//			User user = userOpt.get();
//			System.out.println(user.getUserName());
//			model.addAttribute("user", user);

			if (role == ROLE.ADMIN)
				return "redirect:/user/adminDashboard";

			if (role == ROLE.AGENT)
				return "redirect:/user/agentDashboard";

			if (role == ROLE.CLAIM_ADJUSTER)
				return "redirect:/user/adjusterDashboard";

			if (role == ROLE.POLICYHOLDER)
				return "redirect:/user/userDashboard";
		}

		model.addAttribute("error", "Invalid credentials or role");
		if (role == ROLE.ADMIN)
			return "loginAdmin";

		if (role == ROLE.AGENT)
			return "loginAgent";

		if (role == ROLE.CLAIM_ADJUSTER)
			return "loginAdjuster";

		if (role == ROLE.POLICYHOLDER)
			return "loginUser";

		return "selectRole"; // fallback only if needed
	}

//------------------------------------------------RETRIVE USER--------------------------------------------------------//

	@GetMapping("/profile/{userId}")
	public String getUserProfile(@PathVariable Long userId, Model model) {

		var userOpt = usImpl.getUserProfile(userId);

		if (userOpt.isPresent()) {
			model.addAttribute("user", userOpt.get());
			return "profile";

		} else {
			model.addAttribute("error", "User not found");
			return "error";
		}
	}

//------------------------------------------------LOGOUT USER--------------------------------------------------------//

	@GetMapping("/logout")
	public String logoutUser(HttpSession session) {
		session.invalidate(); // Clears all session data
		return "redirect:/"; // Redirect to home page or login
	}

//------------------------------------------------------------------------------------------------------------------//

}
