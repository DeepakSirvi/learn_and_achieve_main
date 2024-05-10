package com.dollop.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.app.payload.QuestionBankRequest;
import com.dollop.app.service.QuestionBankService;
import com.dollop.app.util.AppConstant;

@RestController
@RequestMapping("api/question")
@CrossOrigin
public class QuestionBankController {

	@Autowired
	private QuestionBankService questionBankService;

	@PostMapping("/")
	public ResponseEntity<?> createQuestionBank(@Validated @RequestBody QuestionBankRequest questionBankRequest) {
		return new ResponseEntity<>(questionBankService.addQuestionBank(questionBankRequest), HttpStatus.CREATED);
	}

	@GetMapping("/{questionBankId}")
	public ResponseEntity<?> getQuestionBank(@PathVariable String questionBankId) {
		return new ResponseEntity<>(questionBankService.getQuestionBank(questionBankId), HttpStatus.OK);
	}

	@GetMapping("/getAllQuestionBank")
	public ResponseEntity<?> getAllQuestionBank(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer pageSize) {
		return new ResponseEntity<>(questionBankService.getAllQuestionBank(search, pageIndex, pageSize), HttpStatus.OK);
	}

	@PutMapping("/")
	public ResponseEntity<?> updateQuestionBank(@Validated @RequestBody QuestionBankRequest questionBankRequest) {
		return new ResponseEntity<>(questionBankService.updateQuestionBank(questionBankRequest), HttpStatus.OK);
	}

	@DeleteMapping("/{questionBankId}")
	public ResponseEntity<?> deleteQuestionBank(@PathVariable String questionBankId) {
		return new ResponseEntity<>(questionBankService.deleteQuestionBank(questionBankId), HttpStatus.OK);
	}

	@PatchMapping("/{questionBankId}/{status}")
	public ResponseEntity<?> updateStatusQuestionBank(@PathVariable String questionBankId,
			@PathVariable Boolean status) {
		return new ResponseEntity<>(questionBankService.updateStatusQuestionBank(questionBankId, status),
				HttpStatus.OK);
	}
	
	 @PostMapping("/upload")
	    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) {
		System.err.println("dsd");
		 return new ResponseEntity<>(questionBankService.saveinbulk(file),HttpStatus.CREATED);
	    }

	
}
