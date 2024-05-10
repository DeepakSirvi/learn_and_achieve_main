package com.dollop.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class QuestionBank {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String questionId;
	
	@Column(length = 1000,nullable = false)
	private String question;
	
	@Column(nullable = false)
	private String medium;
	@Column(nullable = false,length = 1000)
	private String option1;
	@Column(nullable = false,length=1000)
	private String option2;
	
	@Column(length = 1000)
	private String option3;
	@Column(length = 1000)
	private String option4;
	
	@ManyToOne
	@JoinColumn(name = "classMaster")
	private ClassMaster classMaster;
	
	@ManyToOne
	@JoinColumn(name = "subject")
	private Subject subject;
	
	@Column(length = 1000,nullable = false)
	private String solution;
	
	@Column(nullable = false,length = 1000)
	private String answer;
	
	@Column(nullable = false)
	private String questionType;
	private Boolean status;
	
}
