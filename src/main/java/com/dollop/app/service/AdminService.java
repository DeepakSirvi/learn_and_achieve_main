package com.dollop.app.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dollop.app.payload.AdminRequest;
import com.dollop.app.payload.ChangePasswordRequest;
import com.dollop.app.payload.ForgetPasswordRequest;
import com.dollop.app.payload.LoginRequest;
import com.dollop.app.payload.OtpRequest;
import com.dollop.app.payload.UpdateAdminRequest;

public interface AdminService {
	
	public Map<String,Object> addAdmin(AdminRequest adminRequest);
	public Map<String,Object> generateOpt(OtpRequest otpRequest);
	public Map<String,Object> loginUser(LoginRequest loginRequest);
	public Map<String,Object> generateOptForForgetPassword(String email);
	public Map<String,Object>  updatePassword(ForgetPasswordRequest request);
	public Map<String,Object> updateUser(AdminRequest adminRequest,MultipartFile file);
	public  Map<String,Object>  changePassword(ChangePasswordRequest request,String userId);
	public Map<String,Object> verifiedOtp(LoginRequest loginRequest);
	public Map<String,Object> uploadProfile(MultipartFile image, String userId);
	public Map<String,Object> getUserById(String userId);
	
	

}
