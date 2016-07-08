package com.ilts.anywhere.authentication;

import java.util.Random;

public class User {

	private String userId;
	private String userName;
	private String password;
	private String role;
	private String status;

	private String firstName;
	private String lastName;
	private String birthDate;
	private String gender;

	public User() {

	}

	public String getUserId() {
		return this.userId;
	}
	
	public void setUserID(String userID) {
		this.userId = userID;
	}

	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getBirthDate() {
		return this.birthDate;
	}
	
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}