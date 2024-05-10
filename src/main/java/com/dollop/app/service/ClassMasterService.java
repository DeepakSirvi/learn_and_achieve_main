package com.dollop.app.service;

import java.util.Map;

import com.dollop.app.payload.ClassMasterRequest;

public interface ClassMasterService {
	
	public Map<String,Object> addClassMaster(ClassMasterRequest request);
	public Map<String,Object> getClassMaster(String classId);
	public Map<String,Object> getAllClassMaster(String search, Integer pageIndex, Integer pageSize);
	public Map<String,Object> getAllActiveClassMaster();
	public Map<String,Object> updateClassMaster(ClassMasterRequest request);
	public Map<String,Object> deleteClassMaster(String classId);
	public Map<String,Object> updateStatusClassMaster(String classId, Boolean status);
	


}
