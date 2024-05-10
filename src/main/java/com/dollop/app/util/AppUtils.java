package com.dollop.app.util;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dollop.app.exception.BadRequestException;

@Component
public class AppUtils {
	
	@Autowired
	public Cloudinary  cloudinary;
	
	public static String TYPE = "text/csv";
	public static Integer generateOtp() {
		Random r = new Random();
		return r.nextInt(100000, 999999);
	}
	
	public static final void validatePageAndSize(Integer page, Integer size) {

		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size <= 0) {
			throw new BadRequestException("Size number cannot be less than zero.");
		}

		if (size > AppConstant.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstant.MAX_PAGE_SIZE);
		}
	}
	
	

	public  String uploadFileInFolder(MultipartFile myFile, String destinationPath) {
		String randomName= (UUID.randomUUID().toString() + myFile.getOriginalFilename());
		String fileName = StringUtils.cleanPath(randomName);
		Map uploadResponse;
		try {
			uploadResponse = cloudinary.uploader().upload(myFile.getBytes(),
					  ObjectUtils.asMap("public_id", destinationPath +"/"+fileName)); 
			return (String)uploadResponse.get("secure_url");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	


}
