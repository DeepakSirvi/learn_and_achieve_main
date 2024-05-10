package com.dollop.app.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassMasterRequest {
	
	
	private String classId;
	@NotBlank
	private String className;
	@NotBlank
	private String endDate;

}
