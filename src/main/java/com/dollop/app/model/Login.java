package com.dollop.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Login {

	@Id
//	@GeneratedValue(strategy = GenerationType.UUID)
//	private String id;
	private String userEmail;
	@Column(nullable=false)
	private Integer otp;
	private LocalDateTime createdAt;
	private LocalDateTime experiedAt;
}
