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
public class QuestionBankRequest {
	
	private String questionId;
	
	private String question;
	
	private String medium;
	@NotBlank
	private String option1;
	@NotBlank
	private String option2;
	private String option3;
	private String option4;
	private String classMasterId;
	
	private String subjectId;

	private String solution;
	private String answer;
	private String questionType;
	
	private Boolean status;
}
