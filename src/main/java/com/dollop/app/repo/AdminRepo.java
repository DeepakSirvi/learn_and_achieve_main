package com.dollop.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, String> {

	public Boolean existsByUserEmail(String email);
	public Optional<Admin> findByUserEmailAndUserPassword(String userEmail, String userPassword);
	public Optional<Admin> findByUserEmail(String userEmail);
	public boolean existsByUserEmailAndUserIdNot(String userEmail, String userId);

}
