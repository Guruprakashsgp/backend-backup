package com.example.Repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Admin;

@Qualifier("Admin")
public interface AdminRepo extends JpaRepository<Admin,Long>{

	List<Admin> findByEmail(String email);

	List<Admin> findByUserName(String username);

	List<Admin> findByEmailAndUserName(String email1, String username1);

	Admin findByUserNameAndPassword(String userName, String password);

	

//	List<Admin> getByEmail(String email);

}
