package com.dollop.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.app.model.ClassMaster;
import com.dollop.app.model.Subject;

public interface SubjectRepo extends JpaRepository<Subject, String> {

	boolean existsBySubjectName(String subjectName);

	@Query("Select s from Subject s where LOWER(s.subjectName) LIKE LOWER(concat('%', :searchTerm, '%'))")
	Page<Subject> findBySubjectDetails(@Param(value = "searchTerm") String search, Pageable pageable);

	List<Subject> findByStatus(boolean b);

	boolean existsBySubjectNameAndSubjectIdNot(String subjectName, String subjectId);

	boolean existsBySubjectIdAndStatus(String subjectId, boolean b);

}
