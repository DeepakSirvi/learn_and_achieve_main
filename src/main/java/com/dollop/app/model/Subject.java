package com.dollop.app.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subject {
	
	public Subject(String subjectId) {
		this.subjectId=subjectId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String subjectId;
	
	@Column(nullable = false,unique=true)
	private String subjectName;
	
	private Boolean status;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "subject")
	private List<QuestionBank> questionBank;

}
