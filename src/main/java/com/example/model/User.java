package com.example.model;

public class User {

	public enum ROLE {
		ADMIN, AGENT, CLAIM_ADJUSTER, POLICYHOLDER
	}

	private int userId;
	private String username;
	private String password; // this is to be encrypted
	private ROLE role;
	private String email;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ROLE getRole() {
		return role;
	}

	public void setRole(ROLE role) {
		this.role = role;
	}

	public void fillUserDetails(String username, String password, ROLE role, String email) {
		setUsername(username);
		setPassword(password);
		setRole(role);
		setEmail(email);

	}
	public void printUserDetails() {
		System.out.print(getUsername() + " ");
		System.out.print(getPassword()+" ");
		System.out.print(getRole()+" ");
		System.out.print(getEmail()+" ");
		System.out.println();
	}

}
