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
import org.springframework.web.bind.annotation.RestController;

import com.dollop.app.payload.SubjectRequest;
import com.dollop.app.service.SubjectService;
import com.dollop.app.util.AppConstant;

@RestController
@RequestMapping("api/subject")
@CrossOrigin
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;

	@PostMapping("/")
	public ResponseEntity<?> createSubject(@Validated @RequestBody SubjectRequest subjectRequest) {
		return new ResponseEntity<>(subjectService.addSubject(subjectRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/{subjectId}")
	public ResponseEntity<?> getSubject(@PathVariable String subjectId) {
		return new ResponseEntity<>(subjectService.getSubject(subjectId), HttpStatus.OK);
	}
	
	@GetMapping("/getAllSubject")
	public ResponseEntity<?> getAllSubject(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer pageSize) {
		return new ResponseEntity<>(subjectService.getAllSubject(search,pageIndex,pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/getAllActiveSubject")
	public ResponseEntity<?> getAllSubject() {
		return new ResponseEntity<>(subjectService.getAllActiveSubject(), HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateSubject(@Validated @RequestBody SubjectRequest subjectRequest) {
		return new ResponseEntity<>(subjectService.updateSubject(subjectRequest), HttpStatus.OK);
	}
	

	@DeleteMapping("/{subjectId}")
	public ResponseEntity<?> deleteSubject(@PathVariable String subjectId) {
		return new ResponseEntity<>(subjectService.deleteSubject(subjectId), HttpStatus.OK);
	}
	
	@PatchMapping("/{subjectId}/{status}")
	public ResponseEntity<?> updateStatusSubject(@PathVariable String subjectId,@PathVariable Boolean status) {
		return new ResponseEntity<>(subjectService.updateStatusSubject(subjectId,status), HttpStatus.OK);
	}
	
}
