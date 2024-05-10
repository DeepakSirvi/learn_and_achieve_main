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
public class SubjectRequest {
	
    private String subjectId;
    @NotBlank
	private String subjectName;
	private Boolean status;  

}
