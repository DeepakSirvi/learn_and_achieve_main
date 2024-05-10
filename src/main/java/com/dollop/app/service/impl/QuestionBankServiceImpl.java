package com.dollop.app.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.app.exception.BadRequestException;
import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.model.ClassMaster;
import com.dollop.app.model.QuestionBank;
import com.dollop.app.model.Subject;
import com.dollop.app.payload.PageResponse;
import com.dollop.app.payload.QuestionBankRequest;
import com.dollop.app.payload.QuestionBankResponse;
import com.dollop.app.repo.ClassMasterRepo;
import com.dollop.app.repo.QuestionBankRepo;
import com.dollop.app.repo.SubjectRepo;
import com.dollop.app.service.QuestionBankService;
import com.dollop.app.util.AppConstant;
import com.dollop.app.util.AppUtils;

@Service
public class QuestionBankServiceImpl implements QuestionBankService {
	
	@Autowired
	private QuestionBankRepo questionBankRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SubjectRepo  subjectRepo;
	
	@Autowired
	private ClassMasterRepo classMasterRepo;
	
	@Override
	public Map<String, Object> addQuestionBank(QuestionBankRequest questionBankRequest) {
		if(!classMasterRepo.existsByClassIdAndStatus(questionBankRequest.getClassMasterId(),true))
		{
			throw new ResourceNotFoundException(AppConstant.CLASS_MASTER, AppConstant.ID,questionBankRequest.getClassMasterId());
		}
		if(!subjectRepo.existsBySubjectIdAndStatus(questionBankRequest.getSubjectId(),true))
		{
			throw new ResourceNotFoundException(AppConstant.SUBJECT, AppConstant.ID,questionBankRequest.getSubjectId());
		}
		QuestionBank questionBank = new QuestionBank();
		questionBank.setQuestion(questionBankRequest.getQuestion());
		questionBank.setMedium(questionBankRequest.getMedium());
		questionBank.setSubject(new Subject(questionBankRequest.getSubjectId()));
		questionBank.setClassMaster(new ClassMaster(questionBankRequest.getClassMasterId()));
		questionBank.setQuestionType(questionBankRequest.getQuestionType());
		questionBank.setSolution(questionBankRequest.getSolution());
		questionBank.setStatus(true);
		questionBank.setAnswer(questionBankRequest.getAnswer());
		questionBank.setOption1(questionBankRequest.getOption1());
		questionBank.setOption2(questionBankRequest.getOption2());
		questionBank.setOption3(questionBankRequest.getOption3());
		questionBank.setOption4(questionBankRequest.getOption4());
	   questionBankRepo.save(questionBank);
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE, AppConstant.QUESTION_ADDED);
		return map;
	}

	@Override
	public Map<String, Object> updateQuestionBank(QuestionBankRequest questionBankRequest) {
		Optional<QuestionBank> question = questionBankRepo.findById(questionBankRequest.getQuestionId());
		if (question.isEmpty()) {
			throw new ResourceNotFoundException(AppConstant.QUESTION, AppConstant.ID, questionBankRequest.getQuestionId());
		}
		if(!classMasterRepo.existsByClassIdAndStatus(questionBankRequest.getClassMasterId(),true))
		{
			throw new ResourceNotFoundException(AppConstant.CLASS_MASTER, AppConstant.ID,questionBankRequest.getClassMasterId());
		}
		if(!subjectRepo.existsBySubjectIdAndStatus(questionBankRequest.getSubjectId(),true))
		{
			throw new ResourceNotFoundException(AppConstant.SUBJECT, AppConstant.ID,questionBankRequest.getSubjectId());
		}
		QuestionBank questionBank = question.get(); 
		questionBank.setQuestion(questionBankRequest.getQuestion());
		questionBank.setMedium(questionBankRequest.getMedium());
		questionBank.setSubject(new Subject(questionBankRequest.getSubjectId()));
		questionBank.setClassMaster(new ClassMaster(questionBankRequest.getClassMasterId()));
		questionBank.setQuestionType(questionBankRequest.getQuestionType());
		questionBank.setSolution(questionBankRequest.getSolution());
		questionBank.setOption1(questionBankRequest.getOption1());
		questionBank.setOption2(questionBankRequest.getOption2());
		questionBank.setOption3(questionBankRequest.getOption3());
		questionBank.setOption4(questionBankRequest.getOption4());
		questionBank.setAnswer(questionBankRequest.getAnswer());
		questionBankRepo.save(questionBank);
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE, AppConstant.QUESTION_UPDATED);
		return map;
	}

	@Override
	public Map<String, Object> deleteQuestionBank(String questionBankId) {
		if(questionBankRepo.existsById(questionBankId))
		{
			questionBankRepo.deleteById(questionBankId);
			Map<String, Object> map = new HashMap<>();
			map.put(AppConstant.RESPONE_MESSAGE,AppConstant.QUESTION_DELETED);
			return map;
			
		}
		else
		{
			throw new ResourceNotFoundException(AppConstant.QUESTION, AppConstant.ID,questionBankId);
		}
		
	}

	@Override
	public Map<String, Object> getAllQuestionBank(String search, Integer pageIndex, Integer pageSize) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(pageIndex, pageSize);
		
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<QuestionBank> questionList = null;
		if (!search.equals("")) {
			questionList = questionBankRepo.findByQuestionDetails(search, pageable);
		} else {
			questionList = questionBankRepo.findAll(pageable);
		}
		List<QuestionBankResponse> responses = questionList.getContent().stream().map(question -> {

			return modelMapper.map(question, QuestionBankResponse.class);
		}).collect(Collectors.toList());

		PageResponse<QuestionBankResponse> pageResponse = new PageResponse<>();
		pageResponse.setContent(responses);
		pageResponse.setSize(pageSize);
		pageResponse.setPage(pageIndex);
		pageResponse.setTotalElements(questionList.getTotalElements());
		pageResponse.setTotalPages(questionList.getTotalPages());
		pageResponse.setLast(questionList.isLast());
		pageResponse.setFirst(questionList.isFirst());
		response.put("questionList", pageResponse);
		return response;
	}

	@Override
	public Map<String, Object> getQuestionBank(String questionBankId) {
		Optional<QuestionBank> question = questionBankRepo.findById(questionBankId);
		if (question.isEmpty()) {
			throw new ResourceNotFoundException(AppConstant.QUESTION, AppConstant.ID, questionBankId);
		}
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.QUESTION, modelMapper.map(question.get(), QuestionBankResponse.class));
		return map;
	}



	@Override
	public Map<String, Object> updateStatusQuestionBank(String questionBankId, Boolean status) {
		Optional<QuestionBank> question = questionBankRepo.findById(questionBankId);
		if(question.isEmpty())
		{
			throw new ResourceNotFoundException(AppConstant.QUESTION, AppConstant.ID, questionBankId);
		}
		if(question.get().getStatus().equals(status))
		{
			throw new BadRequestException(AppConstant.INVALID_TRANSCATION);
		}
		question.get().setStatus(status);;
		questionBankRepo.save(question.get());
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE,AppConstant.QUESTION_UPDATED);
		return map;
	}


	public Map<String,Object> saveinbulk(MultipartFile file) {
		System.err.println("Deepak");
		 if (file.isEmpty()) {
			 	 throw new BadRequestException(AppConstant.SELECT_FILE);
	        }

	        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
	            Sheet sheet = workbook.getSheetAt(0);
	            Iterator<Row> iterator = sheet.iterator();

	            List<QuestionBank> questionsList = new ArrayList<QuestionBank>();
	            List<Integer> rowNotAdded = new ArrayList<Integer>();
	            while (iterator.hasNext()) {
	                Row currentRow = iterator.next();
	                if (currentRow.getRowNum() == 0) {
	                    continue; 
	                }
	                
	                QuestionBank question = new QuestionBank();
	                question.setQuestion(currentRow.getCell(0).getStringCellValue());
	                question.setOption1(currentRow.getCell(1).getStringCellValue());
	                question.setOption2(currentRow.getCell(2).getStringCellValue());
	                question.setOption3(currentRow.getCell(3).getStringCellValue());
	                question.setOption4(currentRow.getCell(4).getStringCellValue());
	                question.setAnswer(currentRow.getCell(5).getStringCellValue());
	                question.setMedium(currentRow.getCell(6).getStringCellValue());
	                question.setQuestionType(currentRow.getCell(7).getStringCellValue());
	                question.setSolution(currentRow.getCell(8).getStringCellValue());
	            	question.setSubject(new Subject(currentRow.getCell(9).getStringCellValue()));
	        		question.setClassMaster(new ClassMaster(currentRow.getCell(10).getStringCellValue()));
	                question.setStatus(true);
	                if(!classMasterRepo.existsByClassIdAndStatus(currentRow.getCell(10).getStringCellValue(),true))
	        		{
	                	rowNotAdded.add(currentRow.getRowNum());
//	        			throw new ResourceNotFoundException(AppConstant.CLASS_MASTER, AppConstant.ID,questionBankRequest.getClassMasterId());
	        		}	
	                else if(!subjectRepo.existsBySubjectIdAndStatus(currentRow.getCell(9).getStringCellValue(),true))
	        		{
	                	rowNotAdded.add(currentRow.getRowNum());
//	        			throw new ResourceNotFoundException(AppConstant.SUBJECT, AppConstant.ID,questionBankRequest.getSubjectId());
	        		}
	        		else
	                questionsList.add(question);
	            }
	    		 this.questionBankRepo.saveAll(questionsList);
	    		 Map<String,Object> map = new HashMap<>();
	    		 map.put("notAdded", rowNotAdded);
	    		 map.put(AppConstant.RESPONE_MESSAGE, AppConstant.FILE_UPLOADED);
	            return map;
	        } catch (IOException e) {
	        	throw new BadRequestException(AppConstant.FAIL_TO_UPLOAD);
	        }

	}
}
