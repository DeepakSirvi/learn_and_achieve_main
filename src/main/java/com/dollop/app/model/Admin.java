package com.dollop.app.model;

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
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String userId;
	@Column(nullable=false)
	private String userName;
	@Column(nullable=false,unique = true)
	private String userEmail;
	@Column(nullable=false)
	private String userPhone;
	private String userRole;
	@Column(nullable=false)
	private String userPassword;
	private String userProfile;

}
