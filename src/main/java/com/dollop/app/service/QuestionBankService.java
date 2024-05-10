package com.dollop.app.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dollop.app.payload.QuestionBankRequest;

public interface QuestionBankService {

	public Map<String,Object> updateQuestionBank(QuestionBankRequest questionBankRequest);

	public Map<String,Object> deleteQuestionBank(String questionBankId);

	public Map<String,Object> getAllQuestionBank(String search, Integer pageIndex, Integer pageSize);

	public Map<String,Object> getQuestionBank(String questionBankId);

	public Map<String,Object> addQuestionBank(QuestionBankRequest questionBankRequest);

	public Map<String,Object> updateStatusQuestionBank(String questionBankId, Boolean status);

	public Map<String,Object> saveinbulk(MultipartFile file);

}
