package com.spice.smsotpms.service;

import com.spice.smsotpms.entity.SMSOtp;
import com.spice.smsotpms.utility.GenericResponse;

public interface SmsOtpService {
	
	GenericResponse generateOtp(SMSOtp otp) throws Exception;
	
	GenericResponse validateOtp(SMSOtp otp) throws Exception;

	GenericResponse getDefaultNumber() throws Exception;
}
