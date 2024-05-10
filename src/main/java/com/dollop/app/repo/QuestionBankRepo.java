package com.dollop.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.app.model.QuestionBank;

public interface QuestionBankRepo extends JpaRepository<QuestionBank ,String> {

	  @Query("SELECT qb FROM QuestionBank qb " +
	           "JOIN qb.classMaster cm " +
	           "JOIN qb.subject s " +
	           "WHERE cm.className LIKE %:searchTerm% " +
	           "OR s.subjectName LIKE %:searchTerm% " +
	           "OR qb.medium LIKE %:searchTerm% ")
	Page<QuestionBank> findByQuestionDetails(@Param(value="searchTerm") String search, Pageable pageable);

}
