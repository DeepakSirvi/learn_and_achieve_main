package com.dollop.app.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
	
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmPassword;
	@NotBlank
	private String currentPassword;

}
