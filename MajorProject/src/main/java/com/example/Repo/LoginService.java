package com.example.Repo;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginService {
	
	public int loginvalidation(String UserName,String Password)throws SQLException;
	
	public int sendEmail(String toEmail);

	public String addCandidate(String name,String username,String password,String email);
	
	public String candidatePresent(String username,String email) throws SQLException;
	
	public boolean emailPresent(String email) throws SQLException;
	
	public String updatepassword(String password,String email) throws SQLException;
	
}
