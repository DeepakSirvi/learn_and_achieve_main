package com.dollop.app.service;

import java.util.Map;

import com.dollop.app.payload.SubjectRequest;

public interface SubjectService {

	Map<String, Object> addSubject(SubjectRequest subjectRequest);

	Map<String, Object> getSubject(String subjectId);

	Map<String, Object> getAllSubject(String search, Integer pageIndex, Integer pageSize);

	Map<String, Object> getAllActiveSubject();

	Map<String, Object> updateSubject(SubjectRequest subjectRequest);

	Map<String, Object> deleteSubject(String subjectId);

	Map<String, Object> updateStatusSubject(String subjectId, Boolean status);

}
