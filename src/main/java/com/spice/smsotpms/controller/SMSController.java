package com.spice.smsotpms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spice.smsotpms.entity.SMSOtp;
import com.spice.smsotpms.service.SmsOtpService;
import com.spice.smsotpms.service.UserService;
import com.spice.smsotpms.utility.ResponseHeaderUtility;

@RestController
@RequestMapping("/otp")
public class SMSController {
	
	@Autowired
	private SmsOtpService smsOtpService;	
	
	@Autowired
	private UserService userService;
		
	@PostMapping("/generateotp")
	public ResponseEntity<Object> generateOtp(@RequestBody SMSOtp otp) throws Exception{ 
		Object obj = smsOtpService.generateOtp(otp);
		return new ResponseEntity<Object>(obj,ResponseHeaderUtility.HttpHeadersConfig(),HttpStatus.CREATED) ;
	}
	
	@PostMapping("/validateotp")
	public ResponseEntity<Object> validateOtp(@RequestBody SMSOtp otp) throws Exception{
		Object object = smsOtpService.validateOtp(otp);
		return new ResponseEntity<Object>(object,ResponseHeaderUtility.HttpHeadersConfig(),HttpStatus.OK);
	}
	
	@GetMapping("/test/testapi")
	public String testToken() {
		return "valid token";
	}
	
	@GetMapping("/defaultnumber")
	public ResponseEntity<Object> getDefaultNumber() throws Exception{
		Object object = smsOtpService.getDefaultNumber();
		return new ResponseEntity<Object>(object,ResponseHeaderUtility.HttpHeadersConfig(),HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<Object> getUsers() throws Exception{
		Object object = userService.getUsers();
		return new ResponseEntity<Object>(object,ResponseHeaderUtility.HttpHeadersConfig(),HttpStatus.OK);
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<Object> userDetails(@PathVariable("username") String userName) throws Exception{
		Object object = userService.userDetails(userName);
		return new ResponseEntity<Object>(object,ResponseHeaderUtility.HttpHeadersConfig(),HttpStatus.OK);
	}
}
