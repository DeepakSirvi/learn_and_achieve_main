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
import com.dollop.app.model.Subject;
import com.dollop.app.payload.PageResponse;
import com.dollop.app.payload.SubjectRequest;
import com.dollop.app.payload.SubjectResponse;
import com.dollop.app.repo.SubjectRepo;
import com.dollop.app.service.SubjectService;
import com.dollop.app.util.AppConstant;
import com.dollop.app.util.AppUtils;

@Service
public class SubjectServiceImpl implements SubjectService {
	

	@Autowired
	private SubjectRepo subjectRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Map<String, Object> addSubject(SubjectRequest subjectRequest) {
		if(subjectRepo.existsBySubjectName(subjectRequest.getSubjectName()))
		{
			throw new DuplicateEntryException(AppConstant.SUBJECT_FOUND);
		}
		Subject subject = modelMapper.map(subjectRequest, Subject.class);	
		subject.setStatus(true);
		subjectRepo.save(subject);
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE, AppConstant.SUBJECT_ADDED);
		return map;
	}

	@Override
	public Map<String, Object> getSubject(String subjectId) {
		Optional<Subject> subject = subjectRepo.findById(subjectId);
		if (subject.isEmpty()) {
			throw new ResourceNotFoundException(AppConstant.SUBJECT, AppConstant.ID, subjectId);
		}
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.SUBJECT, modelMapper.map(subject.get(), SubjectResponse.class));
		return map;
	}

	@Override
	public Map<String, Object> getAllSubject(String search, Integer pageIndex, Integer pageSize) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(pageIndex, pageSize);
		
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<Subject> subjectList = null;
		if (!search.equals("")) {
			subjectList = subjectRepo.findBySubjectDetails(search, pageable);
		} else {
			subjectList = subjectRepo.findAll(pageable);
		}
		List<SubjectResponse> responses = subjectList.getContent().stream().map(subject -> {

			return modelMapper.map(subject, SubjectResponse.class);
		}).collect(Collectors.toList());

		PageResponse<SubjectResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(responses);
		pageResponse.setSize(pageSize);
		pageResponse.setPage(pageIndex);
		pageResponse.setTotalElements(subjectList.getTotalElements());
		pageResponse.setTotalPages(subjectList.getTotalPages());
		pageResponse.setLast(subjectList.isLast());
		pageResponse.setFirst(subjectList.isFirst());
		response.put("subjectList", pageResponse);
		return response;
	}

	@Override
	public Map<String, Object> getAllActiveSubject() {
		List<Subject> subjectList = subjectRepo.findByStatus(true);
		List<SubjectResponse> responses = subjectList.stream().map(subject -> {

			return modelMapper.map(subject, SubjectResponse.class);
		}).collect(Collectors.toList());
		Map<String, Object> map = new HashMap<>();
		map.put("SubjectList", responses);
		return map;
	}

	@Override
	public Map<String, Object> updateSubject(SubjectRequest subjectRequest) {
		Optional<Subject> subject = subjectRepo.findById(subjectRequest.getSubjectId());
		if (subject.isEmpty()) {
			throw new ResourceNotFoundException(AppConstant.SUBJECT, AppConstant.ID,subjectRequest.getSubjectId());
		}
		
		if(subjectRepo.existsBySubjectNameAndSubjectIdNot(subjectRequest.getSubjectName(),subjectRequest.getSubjectId()))
		{
			throw new DuplicateEntryException(AppConstant.SUBJECT_FOUND);
		}
		
		Subject master = subject.get();
		master.setSubjectName(subjectRequest.getSubjectName());
		subjectRepo.save(master);
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE,AppConstant.SUBJECT_UPDATED);
		return map;
	}

	@Override
	public Map<String, Object> deleteSubject(String subjectId) {
		if(subjectRepo.existsById(subjectId))
		{
			subjectRepo.deleteById(subjectId);
			Map<String, Object> map = new HashMap<>();
			map.put(AppConstant.RESPONE_MESSAGE,AppConstant.SUBJECT_DELETED);
			return map;
			
		}
		else
		{
			throw new ResourceNotFoundException(AppConstant.SUBJECT, AppConstant.ID,subjectId);
		}
		
	}

	@Override
	public Map<String, Object> updateStatusSubject(String subjectId, Boolean status) {
		Optional<Subject> subject = subjectRepo.findById(subjectId);
		if(subject.isEmpty())
		{
			throw new ResourceNotFoundException(AppConstant.SUBJECT, AppConstant.ID, subjectId);
		}
		if(subject.get().getStatus().equals(status))
		{
			throw new BadRequestException(AppConstant.INVALID_TRANSCATION);
		}
		subject.get().setStatus(status);
		subjectRepo.save(subject.get());
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE,AppConstant.SUBJECT_UPDATED);
		return map;
	}

}
