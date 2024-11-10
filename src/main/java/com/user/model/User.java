package com.user.model;

public class User {

	private int userId;
	private String name;
	private String email;
	private String passwordHash;
	private String role;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userId, String name, String email, String passwordHash, String role) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", passwordHash=" + passwordHash
				+ "]";
	}

}
