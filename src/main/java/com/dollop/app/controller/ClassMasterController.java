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

import com.dollop.app.payload.ClassMasterRequest;
import com.dollop.app.service.ClassMasterService;
import com.dollop.app.util.AppConstant;

@RestController
@RequestMapping("api/classMaster")
@CrossOrigin
public class ClassMasterController {
	
	@Autowired
	private ClassMasterService classMasterService;

	@PostMapping("/")
	public ResponseEntity<?> createClassMaster(@Validated @RequestBody ClassMasterRequest classMasterRequest) {
		return new ResponseEntity<>(classMasterService.addClassMaster(classMasterRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/{classMasterId}")
	public ResponseEntity<?> getClassMaster(@PathVariable String classMasterId) {
		return new ResponseEntity<>(classMasterService.getClassMaster(classMasterId), HttpStatus.OK);
	}
	
	@GetMapping("/getAllClassMaster")
	public ResponseEntity<?> getAllClassMaster(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer pageSize){
	return new ResponseEntity<>(classMasterService.getAllClassMaster(search,pageIndex,pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/getAllActiveClassMaster")
	public ResponseEntity<?> getAllClassMaster() {
		return new ResponseEntity<>(classMasterService.getAllActiveClassMaster(), HttpStatus.OK);
	}
	
	@PutMapping("/")
	public ResponseEntity<?> updateClassMaster(@Validated @RequestBody ClassMasterRequest classMasterRequest) {
		return new ResponseEntity<>(classMasterService.updateClassMaster(classMasterRequest), HttpStatus.OK);
	}
	

	@DeleteMapping("/{classMasterId}")
	public ResponseEntity<?> deleteClassMaster(@PathVariable String classMasterId) {
		return new ResponseEntity<>(classMasterService.deleteClassMaster(classMasterId), HttpStatus.OK);
	}
	
	@PatchMapping("/{classId}/{status}")
	public ResponseEntity<?> updateStatusClassMaster(@PathVariable String classId,@PathVariable Boolean status) {
		return new ResponseEntity<>(classMasterService.updateStatusClassMaster(classId,status), HttpStatus.OK);
	}
	
}
