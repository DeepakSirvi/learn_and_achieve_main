package com.dollop.app.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.app.exception.BadRequestException;
import com.dollop.app.exception.DuplicateEntryException;
import com.dollop.app.exception.ResourceNotFoundException;
import com.dollop.app.model.Admin;
import com.dollop.app.model.Login;
import com.dollop.app.payload.AdminRequest;
import com.dollop.app.payload.AdminResponse;
import com.dollop.app.payload.ChangePasswordRequest;
import com.dollop.app.payload.ForgetPasswordRequest;
import com.dollop.app.payload.LoginRequest;
import com.dollop.app.payload.OtpRequest;
import com.dollop.app.payload.UpdateAdminRequest;
import com.dollop.app.repo.AdminRepo;
import com.dollop.app.repo.LoginRepo;
import com.dollop.app.service.AdminService;
import com.dollop.app.util.AppConstant;
import com.dollop.app.util.AppUtils;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepo adminRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LoginRepo loginRepo;

	@Autowired
	private AppUtils appUtils;

	@Override
	public Map<String, Object> addAdmin(AdminRequest adminRequest) {
		if (adminRepo.existsByUserEmail(adminRequest.getUserEmail())) {
			throw new DuplicateEntryException(AppConstant.DUPLICATE_EMAIL_FOUND);
		}
		adminRepo.save(modelMapper.map(adminRequest, Admin.class));
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE, AppConstant.RESGISTRATION_SUCCESSFULL);
		return map;
	}

	@Override
	public Map<String, Object> generateOpt(OtpRequest otpRequest) {

		if (adminRepo.existsByUserEmail(otpRequest.getUserEmail())) {
			Optional<Admin> user = adminRepo.findByUserEmailAndUserPassword(otpRequest.getUserEmail(),
					otpRequest.getUserPassword());
			if (user.isPresent()) {
				Integer otp = AppUtils.generateOtp();
				Login login = new Login();
				Optional<Login> oldLogin = loginRepo.findById(otpRequest.getUserEmail());
				if (oldLogin.isPresent()) {
					login.setUserEmail(oldLogin.get().getUserEmail());
				} else
					login.setUserEmail(otpRequest.getUserEmail());
				login.setOtp(otp);
				login.setCreatedAt(LocalDateTime.now());
				login.setExperiedAt(LocalDateTime.now().plusMinutes(15));
				loginRepo.save(login);
				Map<String, Object> map = new HashMap<>();
				map.put("OTP", otp);
				return map;
			} else {
				throw new BadRequestException(AppConstant.INCORRECT_PASSWORD);
			}
		} else {
			throw new BadRequestException(AppConstant.NEW_USER);
		}
	}

	@Override
	public Map<String, Object> loginUser(LoginRequest loginRequest) {
		if (loginRepo.existsByUserEmail(loginRequest.getUserEmail())) {
			Optional<Login> login = loginRepo.findByUserEmailAndOtp(loginRequest.getUserEmail(), loginRequest.getOtp());
			if (login.isPresent()) {
				if (login.get().getExperiedAt().compareTo(LocalDateTime.now()) >= 0) {

					Admin user = adminRepo.findByUserEmail(login.get().getUserEmail()).get();
					AdminResponse currentUser = modelMapper.map(user, AdminResponse.class);
					currentUser.setUserPassword(null);
					Map<String, Object> map = new HashMap<>();
					map.put(AppConstant.USER, currentUser);
					return map;
				} else {
					throw new BadRequestException(AppConstant.OTP_EXPERED);
				}
			} else {
				throw new BadRequestException(AppConstant.INVALID_OTP);
			}

		} else {
			throw new BadRequestException(AppConstant.INVALID_EMAIL);
		}
	}

	@Override
	public Map<String, Object> generateOptForForgetPassword(String email) {
		String regexPattern = "^[A-Za-z0-9.]+@[A-Za-z]+.[A-Za-z]{2,}$";

		if (!email.matches(regexPattern)) {
			throw new BadRequestException(AppConstant.INVALID_EMAIL);
		}

		Optional<Admin> user = adminRepo.findByUserEmail(email);
		if (user.isPresent()) {
			Integer otp = AppUtils.generateOtp();
			Login login = new Login();
			Optional<Login> oldLogin = loginRepo.findById(email);
			if (oldLogin.isPresent()) {
				login.setUserEmail(oldLogin.get().getUserEmail());
			} else
				login.setUserEmail(email);
			login.setOtp(otp);
			login.setCreatedAt(LocalDateTime.now());
			login.setExperiedAt(LocalDateTime.now().plusMinutes(15));
			loginRepo.save(login);
			Map<String, Object> map = new HashMap<>();
			map.put("OTP", otp);
			return map;
		} else {
			throw new BadRequestException(AppConstant.NEW_USER);
		}
	}

	@Override
	public Map<String, Object> updatePassword(ForgetPasswordRequest request) {
		Map<String, Object> map = new HashMap<>();
		LoginRequest loginRequest = new LoginRequest(request.getUserEmail(), request.getOtp());
		if (loginUser(loginRequest).size() != 0) {
			if (request.getConfirmPassword().equals(request.getNewPassword())) {
				Optional<Admin> oldUser = adminRepo.findByUserEmail(request.getUserEmail());
				if (request.getNewPassword().equals(oldUser.get().getUserPassword())) {
					throw new BadRequestException(AppConstant.OLD_NEW_PASS_MATCH);
				}
				Admin admin = oldUser.get();
				admin.setUserPassword(request.getNewPassword());
				adminRepo.save(admin);
				map.put(AppConstant.RESPONE_MESSAGE, AppConstant.PASWWORD_UPDATE);
			} else {
				throw new BadRequestException(AppConstant.PASSWORD_NOT_MATCH);
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> updateUser(AdminRequest adminRequest, MultipartFile file) {
		Optional<Admin> user = adminRepo.findById(adminRequest.getUserId());
		if (user.isPresent()) {
			if (adminRepo.existsByUserEmailAndUserIdNot(adminRequest.getUserEmail(), adminRequest.getUserId())) {
				throw new DuplicateEntryException(AppConstant.DUPLICATE_EMAIL_FOUND);
			} else {
				Admin admin = user.get();
				admin.setUserEmail(adminRequest.getUserEmail());
				if (!(admin.getUserEmail().equals(adminRequest.getUserEmail()))) {
					Optional<Login> findByUserEmail = loginRepo.findByUserEmail(admin.getUserEmail());
					findByUserEmail.get().setUserEmail(adminRequest.getUserEmail());
					loginRepo.save(findByUserEmail.get());
					admin.setUserEmail(adminRequest.getUserEmail());
				}
				if (file != null) {
					String profileName = appUtils.uploadFileInFolder(file, "profilePhoto");
					admin.setUserProfile(profileName);
				}
				admin.setUserPhone(adminRequest.getUserPhone());
				admin.setUserName(adminRequest.getUserName());
				adminRepo.save(admin);
				Map<String, Object> map = new HashMap<>();
				map.put(AppConstant.RESPONE_MESSAGE, AppConstant.USER_UPDATED_SUCCESSFULLY);
				return map;
			}
		} else {
			throw new ResourceNotFoundException(AppConstant.USER, AppConstant.ID, adminRequest.getUserId());
		}

	}

	@Override
	public Map<String, Object> changePassword(ChangePasswordRequest request, String userId) {
		Optional<Admin> user = adminRepo.findById(userId);
		if (user.isPresent()) {
			if (request.getConfirmPassword().equals(request.getNewPassword())) {
				Admin admin = user.get();
				if (request.getNewPassword().equals(admin.getUserPassword())) {
					throw new BadRequestException(AppConstant.OLD_NEW_PASS_MATCH);
				}

				if (request.getCurrentPassword().equals(admin.getUserPassword())) {
					Map<String, Object> map = new HashMap<>();
					admin.setUserPassword(request.getNewPassword());
					adminRepo.save(admin);
					map.put(AppConstant.RESPONE_MESSAGE, AppConstant.PASWWORD_UPDATE);
					return map;
				} else {
					throw new BadRequestException(AppConstant.OLD_PASSWORD_NOT_MATCH);

				}

			} else {
				throw new BadRequestException(AppConstant.PASSWORD_NOT_MATCH);
			}
		} else {
			throw new ResourceNotFoundException(AppConstant.USER, AppConstant.ID, userId);
		}

	}

	@Override
	public Map<String, Object> verifiedOtp(LoginRequest loginRequest) {
		if (loginRepo.existsByUserEmail(loginRequest.getUserEmail())) {
			Optional<Login> login = loginRepo.findByUserEmailAndOtp(loginRequest.getUserEmail(), loginRequest.getOtp());
			if (login.isPresent()) {
				if (login.get().getExperiedAt().compareTo(LocalDateTime.now()) >= 0) {
					Map<String, Object> map = new HashMap<>();
					map.put(AppConstant.RESPONE_MESSAGE, "VERIFIED");
					return map;
				} else {
					throw new BadRequestException(AppConstant.OTP_EXPERED);
				}
			} else {
				throw new BadRequestException(AppConstant.INVALID_OTP);
			}

		} else {
			throw new BadRequestException(AppConstant.INVALID_EMAIL);
		}
	}

	@Override
	public Map<String, Object> uploadProfile(MultipartFile image, String userId) {
		Admin user = adminRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstant.USER, AppConstant.ID, userId));

		if (image != null) {
			String profileName = appUtils.uploadFileInFolder(image, "profilePhoto");
			user.setUserProfile(profileName);
			adminRepo.save(user);
		}
		Map<String, Object> map = new HashMap<>();
		map.put(AppConstant.RESPONE_MESSAGE, AppConstant.IMAGE_UPLOADED);
		return map;
	}

	@Override
	public Map<String, Object> getUserById(String userId) {
		Optional<Admin> user = adminRepo.findById(userId);
		if (user.isPresent()) {

			Admin admin = user.get();
			AdminResponse currentUser = modelMapper.map(admin, AdminResponse.class);
			currentUser.setUserPassword(null);
			Map<String, Object> map = new HashMap<>();
			map.put(AppConstant.USER, currentUser);
			return map;
		} else {
			throw new ResourceNotFoundException(AppConstant.USER, AppConstant.ID, userId);
		}
	}
}
