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
public class ClassMaster {

	public ClassMaster(String classMasterId) {
		this.classId = classMasterId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String classId;
	@Column(nullable = false)
	private String className;
	@Column(nullable = false)
	private String endDate;
	private Boolean status;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="classMaster")
	private List<QuestionBank> questionBank;
}
