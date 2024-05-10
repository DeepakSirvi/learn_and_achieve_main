package com.dollop.app.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequest {
	
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmPassword;
	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9.]+@[A-Za-z]+.[A-Za-z]{2,}$")
	private String userEmail;
	private Integer otp;

}
