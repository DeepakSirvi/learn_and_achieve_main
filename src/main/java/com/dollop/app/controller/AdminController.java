package com.dollop.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.PutExchange;

import com.dollop.app.payload.AdminRequest;
import com.dollop.app.payload.ChangePasswordRequest;
import com.dollop.app.payload.ForgetPasswordRequest;
import com.dollop.app.payload.LoginRequest;
import com.dollop.app.payload.OtpRequest;
import com.dollop.app.payload.UpdateAdminRequest;
import com.dollop.app.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@PostMapping("/generateOtp")
	public ResponseEntity<?> getOtpForLogin(@Validated @RequestBody OtpRequest login) {
		return new ResponseEntity<>(adminService.generateOpt(login), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest) {
		return new ResponseEntity<>(adminService.loginUser(loginRequest), HttpStatus.OK);
	}

	
	@GetMapping("/{userEmail}")
	public ResponseEntity<?> getOtpByEmail(@PathVariable(required = true) String userEmail){
		return new ResponseEntity<>(adminService.generateOptForForgetPassword(userEmail), HttpStatus.OK);
	}
	
	@GetMapping("user/{userId}")
	public ResponseEntity<?> getUser(@PathVariable(required = true) String userId){
		return new ResponseEntity<>(adminService.getUserById(userId), HttpStatus.OK);
	}
	
	
	@PostMapping("/setNewPassword")
	public ResponseEntity<?> login(@Validated @RequestBody ForgetPasswordRequest request) {
		return new ResponseEntity<>(adminService.updatePassword(request), HttpStatus.OK);
	}
	
	@PostMapping("/verifyOtp")
	public ResponseEntity<?> veriferiedOtp(@Validated @RequestBody LoginRequest loginRequest) {
		return new ResponseEntity<>(adminService.verifiedOtp(loginRequest), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@Validated @RequestPart(value="user") String user,@RequestPart(value="file",required = false) MultipartFile file) {
		System.err.println("deepak");
		ObjectMapper mapper = new ObjectMapper();
		AdminRequest adminRequest=null;
		try {
			adminRequest = mapper.readValue(user, AdminRequest.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(adminService.updateUser(adminRequest,file), HttpStatus.OK);
	}
	
	@PostMapping("/changePassword/{userId}")
	public ResponseEntity<?> changePassword(@Validated @RequestBody ChangePasswordRequest request,@PathVariable String userId) {
		return new ResponseEntity<>(adminService.changePassword(request,userId), HttpStatus.OK);
	}
	
	@PostMapping("/uploadProfile/{userId}")
	public ResponseEntity<?> updateProfile(@RequestPart(value = "image") MultipartFile image,@PathVariable String userId) {
		return new ResponseEntity<>(adminService.uploadProfile(image,userId), HttpStatus.OK);
	}
}	