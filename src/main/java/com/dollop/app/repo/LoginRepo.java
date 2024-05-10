package com.dollop.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.app.model.Login;

public interface LoginRepo extends JpaRepository<Login, String> {

  	public boolean existsByUserEmail(String userEmail);

	public Optional<Login> findByUserEmailAndOtp(String userEmail, Integer otp);

	public Optional<Login> findByUserEmail(String userEmail);

}
