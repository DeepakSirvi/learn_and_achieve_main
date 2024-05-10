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
public class OtpRequest {
	
	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9.]+@[A-Za-z]+.[A-Za-z]{2,}$")
	private String userEmail;
	@NotBlank
	private String userPassword;

}
