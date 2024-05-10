package com.dollop.app.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.app.model.ClassMaster;

public interface ClassMasterRepo extends JpaRepository<ClassMaster, String> {

	List<ClassMaster> findByStatus(boolean b);

	@Query("Select c from ClassMaster c where LOWER(c.className) LIKE LOWER(concat('%', :searchTerm, '%'))")
	Page<ClassMaster> findByClassMasterDetails(@Param(value = "searchTerm") String search, Pageable pageable);

	boolean existsByClassName(String className);

	boolean existsByClassNameAndClassIdNot(String className, String classId);

	boolean existsByClassIdAndStatus(String classMasterId, boolean b);

}
