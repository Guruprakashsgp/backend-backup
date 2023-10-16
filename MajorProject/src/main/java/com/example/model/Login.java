package com.example.model;

public class Login {

	private String UserName;
	private String Password;
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		this.UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		this.Password = password;
	}
	public Login(String userName, String password) {
		super();
		this.UserName = userName;
		this.Password = password;
	}
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Login [UserName=" + UserName + ", Password=" + Password + "]";
	}
	
	
	
}
