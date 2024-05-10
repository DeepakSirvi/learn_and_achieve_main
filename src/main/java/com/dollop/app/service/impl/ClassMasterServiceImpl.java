package com.dollop.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dollop.app.exception.BadRequestException;
import com.dollop.app.exception.DuplicateEntryException;
import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.model.ClassMaster;
import com.dollop.app.payload.ClassMasterRequest;
import com.dollop.app.payload.ClassMasterResponse;
import com.dollop.app.payload.PageResponse;
import com.dollop.app.repo.ClassMasterRepo;
import com.dollop.app.service.ClassMasterService;
import com.dollop.app.util.AppConstant;
import com.dollop.app.util.AppUtils;


@Service
public class ClassMasterServiceImpl implements ClassMasterService {

	@Autowired
	private ClassMasterRepo classMasterRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Map<String, Object> addClassMaster(ClassMasterRequest request) {
		if(classMasterRepo.existsByClassName(request.getClassName()))
		{
			throw new DuplicateEntryException(AppConstant.CLASSMASTER_FOUND);
		}
		ClassMaster classMaster = modelMapper.map(request, ClassMaster.class);	
		classMaster.setStatus(true);
		classMasterRepo.save(classMaster);
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE, AppConstant.CLASS_ADDED);
		return map;
	}

	@Override
	public Map<String, Object> getClassMaster(String classId) {
		Optional<ClassMaster> classMaster = classMasterRepo.findById(classId);
		if (classMaster.isEmpty()) {
			throw new ResourceNotFoundException(AppConstant.CLASS_MASTER, AppConstant.ID, classId);
		}
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.CLASS_MASTER, modelMapper.map(classMaster.get(), ClassMasterResponse.class));
		return map;
	}

	@Override
	public Map<String, Object> getAllClassMaster(String search, Integer pageIndex, Integer pageSize) {

		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(pageIndex, pageSize);
		
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<ClassMaster> classMasterList = null;
		if (!search.equals("")) {
			classMasterList = classMasterRepo.findByClassMasterDetails(search, pageable);
		} else {
			classMasterList = classMasterRepo.findAll(pageable);
		}
		List<ClassMasterResponse> responses = classMasterList.getContent().stream().map(classMasters -> {

			return modelMapper.map(classMasters, ClassMasterResponse.class);
		}).collect(Collectors.toList());

		PageResponse<ClassMasterResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(responses);
		pageResponse.setSize(pageSize);
		pageResponse.setPage(pageIndex);
		pageResponse.setTotalElements(classMasterList.getTotalElements());
		pageResponse.setTotalPages(classMasterList.getTotalPages());
		pageResponse.setLast(classMasterList.isLast());
		pageResponse.setFirst(classMasterList.isFirst());
		response.put("classMasterList", pageResponse);
		return response;
	}

	@Override
	public Map<String, Object> getAllActiveClassMaster() {
		List<ClassMaster> classMaster = classMasterRepo.findByStatus(true);
		List<ClassMasterResponse> responses = classMaster.stream().map(classMasters -> {

			return modelMapper.map(classMasters, ClassMasterResponse.class);
		}).collect(Collectors.toList());

		Map<String, Object> map = new HashMap<>();
		map.put("ClassMasterList", responses);
		return map;
	}

	@Override
	public Map<String, Object> updateClassMaster(ClassMasterRequest request) {
		Optional<ClassMaster> classMaster = classMasterRepo.findById(request.getClassId());
		if (classMaster.isEmpty()) {
			throw new ResourceNotFoundException(AppConstant.CLASS_MASTER, AppConstant.ID,request.getClassId());
		}
		
		if(classMasterRepo.existsByClassNameAndClassIdNot(request.getClassName(),request.getClassId()))
		{
			throw new DuplicateEntryException(AppConstant.CLASSMASTER_FOUND);
		}
		
		ClassMaster master = classMaster.get();
		master.setClassName(request.getClassName());
		master.setEndDate(request.getEndDate());
		classMasterRepo.save(master);
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE,AppConstant.CLASS_MASTER_UPDAT);
		return map;
	}

	@Override
	public Map<String, Object> deleteClassMaster(String classId) {
//		classMasterRepo.findById(classId).orElseThrow(throw new ResourceNotFoundException(AppConstant.CLASS_MASTER, AppConstant.ID,classId););
		if(classMasterRepo.existsById(classId))
		{
			classMasterRepo.deleteById(classId);
			Map<String, Object> map = new HashMap<>();
			map.put(AppConstant.RESPONE_MESSAGE,AppConstant.CLASSMASTER_DELETED);
			return map;
			
		}
		else
		{
			throw new ResourceNotFoundException(AppConstant.CLASS_MASTER, AppConstant.ID,classId);
		}
		
	
	}

	@Override
	public Map<String, Object> updateStatusClassMaster(String classId, Boolean status) {
		Optional<ClassMaster> classMaster = classMasterRepo.findById(classId);
		if(classMaster.isEmpty())
		{
			throw new ResourceNotFoundException(AppConstant.CLASS_MASTER, AppConstant.ID, classId);
		}
		if(classMaster.get().getStatus().equals(status))
		{
			throw new BadRequestException(AppConstant.INVALID_TRANSCATION);
		}
		classMaster.get().setStatus(status);
		classMasterRepo.save(classMaster.get());
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE,AppConstant.CLASS_MASTER_UPDAT);
		return map;
	}
	

}
