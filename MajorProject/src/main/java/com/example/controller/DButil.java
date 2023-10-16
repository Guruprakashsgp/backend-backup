package com.example.controller;

import java.sql.*;

public class DButil {
	 static Connection connection=null;
	 public static Connection connect(){
		 if(connection!=null)
		 {
			 return connection;
		 }
		 else {
	        try {

	           // String url = "jdbc:mysql://localhost:3306/";
//	        	String driver="com.myaql.cj.jdbc.Driver";
	            String url="jdbc:mysql://localhost:3306/mainproject";
//	            String dataBase ="mainproject";
	            String user = "root";
	            String password = "root";
	            connection = DriverManager.getConnection(url, user, password);
	        }
	        catch (Exception e) 
	        {
	        	e.printStackTrace();

	        }
//	        return connection;
		 }
		return connection;
	    }
}
