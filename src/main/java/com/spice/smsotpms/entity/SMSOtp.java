package com.spice.smsotpms.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sms_otp")
@Getter @Setter
public class SMSOtp {
	
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;
	
	@Id
	@Column(name = "moblie_no")
	private String mobileno;
	
	@Column(name = "otp")
	private String otp;
	
	@Column(name = "otp_time")
	private Date otpTime;

}
