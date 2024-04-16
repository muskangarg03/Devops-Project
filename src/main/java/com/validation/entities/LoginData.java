package com.validation.entities;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginData {

	//Giving the validation on the fields
	
	@NotBlank(message = "User Name can not be empty!!")
	@Size(min=3, max=12, message="User Name must be in between 3-12 characters!!")
	private String userName;
	
	
	//Regular Expression
	@Email(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message="Invalid Email!!")
	private String email;
	
	
	//To check the agreement
	@AssertTrue(message="Must agree terms and conditions!!")
	private boolean agreed;
	
	
	
	public boolean isAgreed() {
		return agreed;
	}
	public void setAgreed(boolean agreed) {
		this.agreed = agreed;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "LoginData [userName=" + userName + ", email=" + email + ", agreed=" + agreed + "]";
	}
	

	
	
	
}
