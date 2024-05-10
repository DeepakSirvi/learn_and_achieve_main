package com.dollop.app.prerequriment;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dollop.app.payload.AdminRequest;
import com.dollop.app.service.AdminService;
import com.dollop.app.util.AppConstant;

//@Component
public class PreRequirment implements CommandLineRunner {

	@Autowired
	private AdminService adminService;

	private static final Logger logger = LoggerFactory.getLogger(PreRequirment.class);

	@Override
	public void run(String... args) throws Exception {

		AdminRequest adminRequest = new AdminRequest();
		adminRequest.setUserName("Deepak");
		adminRequest.setUserEmail("Deepak12@gmail.com");
		adminRequest.setUserPhone("8085661839");
		adminRequest.setUserPassword("1234");
		adminRequest.setUserRole("ADMIN");
		Map<String, Object> save = adminService.addAdmin(adminRequest);
		logger.info(save.get(AppConstant.RESPONE_MESSAGE).toString());
	}
}
