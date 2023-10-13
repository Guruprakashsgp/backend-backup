package com.example.controller;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repo.LoginService;

@RestController
@RequestMapping("/user/")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	
	@Autowired
	private LoginService loginservice;
	
	public UserController(LoginService loginservice) {
		super();
		this.loginservice = loginservice;
	}
	 int flag =0;
	
	 
//	 @CrossOrigin(origins="http://localhost:4200")
	@GetMapping("login/{username}/{password}")
	public boolean userlogin(@PathVariable("username") String username1, @PathVariable("password")String password1) throws SQLException
	{
		flag=loginservice.loginvalidation(username1, password1);
		System.out.println(flag+"hello");
		System.out.println(username1 + password1);
		if(flag==0)
		{
			System.out.println("flag in if");
			return false;
		}
		else
		{
			System.out.println("flag in else");
			//return flag;
			return true;
		}
	}
	 
	
//	 @CrossOrigin(origins="http://localhost:4200")
	 @GetMapping("otp/{email}")
	 public String otpverification(@PathVariable("email") String toEmail) throws SQLException
	 {
		 int otp=0;
		 boolean emailpresent=loginservice.emailPresent(toEmail);;
		 if(emailpresent==true)
		 {
			 otp=loginservice.sendEmail(toEmail);
		 }
		 return JSONObject.quote(String.valueOf(otp));
	 }
	 
//	 @CrossOrigin(origins="http://localhost:4200")
	 @PostMapping("signup/{name}/{username}/{password}/{email}")
	 public String candidateaddition(@PathVariable("name") String name,@PathVariable("username") String username,@PathVariable("password") String password,@PathVariable("email") String email) throws SQLException {
		 String output="";
		 String verify=loginservice.candidatePresent(username, email);
		 if(verify=="good")
		 {
			 loginservice.addCandidate(name, username, password, email);
			 output="Registered";
		 }
		 else if(verify=="username")
		 {
			 output="username";
		 }
		 else if(verify=="email")
		 {
			 output="email";
		 }
		 else
		 {
			 output="username and email";
		 }
		 System.out.println(output);
		 return JSONObject.quote(output);
	 }
	 
	 
	 
		@PutMapping("updateP/{password}/{email}")
		public String updatepass(@PathVariable("password") String password, @PathVariable("email")String email) throws SQLException
		{
		String result=loginservice.updatepassword(password, email);
		return JSONObject.quote(result);
		}
//	 
	 



	

}
