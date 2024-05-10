package com.dollop.app.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AdminResponse {
	
	private String userId;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String userRole;
	private String userPassword;
	private String userProfile;
}
