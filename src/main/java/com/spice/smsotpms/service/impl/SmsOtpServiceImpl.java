package com.spice.smsotpms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spice.smsotpms.entity.EmptyJosn;
import com.spice.smsotpms.entity.SMS;
import com.spice.smsotpms.entity.SMSOtp;
import com.spice.smsotpms.entity.User;
import com.spice.smsotpms.error.MobileNumberNotFoundException;
import com.spice.smsotpms.repository.SmsOtpRepository;
import com.spice.smsotpms.repository.UserRepository;
import com.spice.smsotpms.security.JwtResponse;
import com.spice.smsotpms.security.JwtUtils;
import com.spice.smsotpms.service.SmsOtpService;
import com.spice.smsotpms.utility.GenericResponse;
import com.spice.smsotpms.utility.Library;

@Service
public class SmsOtpServiceImpl implements SmsOtpService{
	
	@Autowired
	private SmsOtpRepository smsOtpRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
		
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	RestTemplate restTemplate;
	
	private static final long OTP_VALID_DURATION = 3 * 60 * 1000;

	@Override
	public GenericResponse generateOtp(SMSOtp otp) throws Exception {
		SMSOtp smsOtp = smsOtpRepository.findByMobileNo(otp.getMobileno()).orElseThrow(() -> new MobileNumberNotFoundException("mobile number not found"));
		User user = userRepository.findByMobileNo(otp.getMobileno()).orElseThrow(() -> new MobileNumberNotFoundException("mobile number not found"));
		SMS sms = new SMS();
		Random random = new Random();
		if(isOTPRequired(smsOtp)) {
			Integer OTP = random.nextInt(999999);
			
			smsOtp.setOtp(String.format("%06d", OTP));
			smsOtp.setOtpTime(new Date());
			smsOtpRepository.save(smsOtp);
			sms.setMessage(String.format("%06d", OTP));
			sms.setMobileno(otp.getMobileno());
			sms.setEmail(user.getEmail());
			user.setPassword(encoder.encode(String.format("%06d", OTP)));
			userRepository.save(user);
		}else {
			sms.setMessage(smsOtp.getOtp());
			sms.setMobileno(smsOtp.getMobileno());
			sms.setEmail(user.getEmail());
		}
		Object obj = smsOtp;
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<SMS> entity = new HttpEntity<SMS>(sms,headers);
//		String restResponse = restTemplate.exchange("http://localhost:3002/notification", HttpMethod.POST,entity,String.class).getBody();
//		System.out.println(restResponse);
		GenericResponse genericResponse=Library.getSuccess(obj,"Record Process success");
		return genericResponse;
	}

	@Override
	public GenericResponse validateOtp(SMSOtp otp) throws Exception{
		GenericResponse response = null;
		SMSOtp smsOtp = smsOtpRepository.findByMobileNo(otp.getMobileno()).orElseThrow(() -> new MobileNumberNotFoundException());
		if(isOtpExpired(smsOtp)) {
			return Library.getFailure("OTP Expired",new EmptyJosn());
		}
		User user = userRepository.findByMobileNo(otp.getMobileno()).orElseThrow(() -> new MobileNumberNotFoundException("mobile number not found"));
		if (encoder.matches(otp.getOtp(), user.getPassword())) {			
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(),otp.getOtp()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

			List<String> roles = userDetailsImpl.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			Object obj = new JwtResponse(jwt, user.getUsername(), user.getMoblieno());
			response = Library.getSuccess(obj, "Token generated for valid otp");
			return response;
		}
		return Library.getFailure("Invalid OTP",new EmptyJosn());
	}
	
	public boolean isOTPRequired(SMSOtp smsOtp) {
        if ( smsOtp.getOtp() == null) {
            return true;
        }
         
        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = smsOtp.getOtpTime().getTime();
         
        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            return true;
        }
         
        return false;
    }

	private boolean isOtpExpired(SMSOtp otp) {
		long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = otp.getOtpTime().getTime();
        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            return true;
        }
        return false;
	}

	@Override
	public GenericResponse getDefaultNumber() throws Exception {
		String mobileNumber = smsOtpRepository.findAll().get(0).getMobileno();
		return Library.getSuccess(mobileNumber, "Default mobile number");
	}
	
}
