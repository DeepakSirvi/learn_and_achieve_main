package com.dollop.app.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class QuestionBankResponse {

	private String questionId;

	private String question;

	private String medium;

	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private ClassMasterResponse classMaster;

	private SubjectResponse subject;

	private String solution;

	private String answer;

	private String questionType;
	private Boolean status;

}
