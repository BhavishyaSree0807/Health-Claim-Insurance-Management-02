
package com.example.projectHCMS.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class User {

	public enum ROLE {
		ADMIN, AGENT, CLAIM_ADJUSTER, POLICYHOLDER
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "User_ID")
	private Long userId;

	@Column(name = "UserName", nullable = false)
	@NotBlank(message = "Username cannot be blank")
	@Pattern(regexp = "^(?=[a-zA-Z][a-zA-Z0-9_]{7,}$)(?=.*_).+",
		    message = "Username must start with a letter, contain only letters, digits, and underscores, include at least one underscore, and be at least 8 characters long")
	private String userName;

	@Column(name = "Password", nullable = false)
	@NotBlank(message = "Password cannot be blank")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long")
	private String password;

	@Column(name = "Role", nullable = false)
	@Enumerated(EnumType.STRING)
	private ROLE role;

	@Column(name = "Email", nullable = false, unique = true)
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email cannot be blank")
	private String email;

//Getters and Setters
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//Controllers
	public User() {
	}

	public User(Long userId, String userName, String password, ROLE role, String email) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.email = email;
	}

}