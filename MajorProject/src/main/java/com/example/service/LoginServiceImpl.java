package com.example.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.Repo.LoginService;
import com.example.controller.DButil;

@Service
public class LoginServiceImpl implements LoginService{
	
//	public LoginServiceImpl()
//	{
//		
//	}
	@Autowired
	private JavaMailSender mailsender;

	@Override
	public int loginvalidation(String UserName, String Password) throws SQLException {
		
		int flag=0;
		Connection conn=DButil.connect();
		String query="select * from demo where (user_name=? and password=?)";
		PreparedStatement ps1;
		ps1 = conn.prepareStatement(query);
		ps1.setString(1,UserName);
		ps1.setString(2, Password);
		ResultSet rs=ps1.executeQuery();
		
		
		
//		Statement st=conn.createStatement();
//		ResultSet rs=st.executeQuery("select * from Login where (username='"+UserName+"' and password='"+Password+"'");
//		System.out.println("hello");
//		System.out.println("hello11");
//		System.out.println(UserName);
//		System.out.println(Password);
		
		
		while (rs.next()){
			rs.getString(1);
			rs.getString(2);
			System.out.println("while");
			if(rs.getString(4).equals(UserName) && rs.getString(3).equals(Password))
			{
				flag=1;
				System.out.println(flag);
			}
			else
			{
				System.out.println("Invalid UserName or Password");
				//System.out.println(flag);
				flag=0;
				System.out.println(flag);
			}
		}
//		conn.close();
		
//		System.out.println(flag);		
//		if(rs.getFetchSize()>0)
//			flag=1;
//		else
//			flag=0;
		
		return flag;
	}
	
	

	@Override
	public int sendEmail(String toEmail) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(toEmail);
		Random rand=new Random();
		int x=111111;
		int y=999999;
		int z=rand.nextInt(y-x+1)+x;
		message.setText(String.valueOf(z));
		message.setSubject("Otp for your Login process");
		message.setFrom("pgparkinglot@gmail.com");
		
		mailsender.send(message);
		
		return z;
		
		
	}



	@Override
	public String addCandidate(String name, String username, String password, String email) {
		Exception error=null;
		try {
		Connection conn=DButil.connect();
		String query="Insert into demo(name,user_name,password,email) values(?,?,?,?)";
		PreparedStatement ps2;
		ps2 = conn.prepareStatement(query);
		ps2.setString(1,name);
		ps2.setString(2, username);
		ps2.setString(3, password);
		ps2.setString(4, email);
		ps2.executeUpdate();
//		conn.close();
		}
		catch(Exception e)
		{
			error=e;
		}
		if(error==null)
		{
			return "Candidate Added";
		}
		else
		{
			return String.valueOf(error);
		}
		
		
		
	}



	@Override
	public String candidatePresent(String username, String email) throws SQLException {
		String result="good";
		int length=3;
		
		Connection conn=DButil.connect();
		String query="select * from demo where (user_name=? and email=?)";
		PreparedStatement ps1;
		ps1 = conn.prepareStatement(query);
		ps1.setString(1,username);
		ps1.setString(2, email);
		
		String query1="select * from demo where user_name=?";
		PreparedStatement ps2;
		ps2=conn.prepareStatement(query1);
		ps2.setString(1, username);
		
		String query2="select * from demo where email=?";
		PreparedStatement ps3;
		ps3=conn.prepareStatement(query2);
		ps3.setString(1, email);
		ResultSet rs;
		for (int i=0;i<length;i++)
		{
			if(i==0)
			{
				rs=ps2.executeQuery();
				if(rs.next()) 
				{
					result="username";
				}
			}
			else if(i==1)
			{
				rs=ps3.executeQuery();
				if(rs.next())
				{
					result="email";
				}
			}
			else
			{
				rs=ps1.executeQuery();
				if(rs.next())
				{
					result="username and email";
				}
				
			}
		}
		return result;
	}



	@Override
	public boolean emailPresent(String email) throws SQLException {
		Connection conn=DButil.connect();
		boolean result=false;
		String query2="select * from demo where email=?";
		PreparedStatement ps3;
		ps3=conn.prepareStatement(query2);
		ps3.setString(1, email);
		ResultSet rs;
		rs=ps3.executeQuery();
		if(rs.next())
		{
			result=true;
		}
		return result;
	}



	@Override
	public String updatepassword(String password,String email) throws SQLException {
		Connection conn=DButil.connect();
		String query="update demo set password=? where email=?";
		PreparedStatement ps=conn.prepareStatement(query);
		ps.setString(1, password);
		ps.setString(2, email);
		ps.executeUpdate();
		return "Updated";
	}
	

	
}
