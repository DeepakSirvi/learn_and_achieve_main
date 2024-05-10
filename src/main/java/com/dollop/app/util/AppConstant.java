package com.dollop.app.util;

import org.springframework.stereotype.Component;

public class AppConstant {

	public static final String RESPONE_MESSAGE = "message";
	
	// Exception message
	public static final String DUPLICATE_EMAIL_FOUND = "This email is already exist please try another email";

	//Registration and login 
	public static final String RESGISTRATION_SUCCESSFULL = "Registration is successfull";
	public static final String NEW_USER = "New User";
	public static final String INVALID_EMAIL = "Inavlid email address";
	public static final String OTP_GENERATED = "OTP Generated Successfully";
	public static final String INVALID_OTP = "OTP is invalid";
	public static final String OTP_EXPERED = "OTP  Expired";
	public static final String INCORRECT_PASSWORD = "Incorrect Password";
	public static final String USER = "currentUser";
	public static final String PASSWORD_NOT_MATCH = "Confiremed and new password not match";
	public static final String PASWWORD_UPDATE = "Password Updated successfully";

	public static final String ID="id";

	public static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully";

	public static final String OLD_PASSWORD_NOT_MATCH = "Old password not match";

	public static final String OLD_NEW_PASS_MATCH = "Current password and user password are same";
	
//	Default value
	public static final String DEFAULT_PAGE_NUMBER = "0";
	public static final String DEFAULT_PAGE_SIZE = "10";
	public static final Integer MAX_PAGE_SIZE = 25;
	public static final String DEFAULT_SORT_DIR = "DESC";

	public static final String CLASS_ADDED = "Class Added successfully";

	public static final String CLASS_MASTER = "classMaster";

	public static  final String CLASS_MASTER_UPDAT="Class Master updated successfully";

	public static final String CLASSMASTER_DELETED = "ClassMaster deleted successfully";

	public static final String CLASSMASTER_FOUND = "Class name is already taken";

	public static final String INVALID_TRANSCATION = "Can not update ";

	public static final Object SUBJECT_ADDED = "Subject added";

	public static final String SUBJECT_FOUND = "Subject Found";

	public static final String SUBJECT = "subject";

	public static final Object SUBJECT_UPDATED = "Subject updated";

	public static final Object SUBJECT_DELETED = "Subject Successfully deleted";

	public static final Object QUESTION_ADDED = "Question added";

	public static final String QUESTION = "question";

	public static final Object QUESTION_UPDATED = "Question updated successfully";

	public static final Object QUESTION_DELETED = "Question deleted successfully";

	public static final String SELECT_FILE = "Please select a file.";

	public static final String FAIL_TO_UPLOAD = "Failed to upload file.";

	public static final Object FILE_UPLOADED = "File uploaded successfully.";

	public static final Object IMAGE_UPLOADED = "Profile photo upload successfully";

	

}
