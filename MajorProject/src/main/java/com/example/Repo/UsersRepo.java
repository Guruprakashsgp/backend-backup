package com.example.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Users;

public interface UsersRepo extends JpaRepository<Users,Long> {

	Users findByUsernameAndPassword(String username1, String password1);

	Users findByEmail(String toEmail);

	Users findByEmailAndUsername(String email, String username);

	Users findByUsername(String username);

	Users getByEmpid(String empid);

}
