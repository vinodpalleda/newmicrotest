package com.spice.smsotpms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spice.smsotpms.entity.SMSOtp;

@Repository
public interface SmsOtpRepository  extends JpaRepository<SMSOtp, String>{

	@Query("select s from SMSOtp s where s.mobileno = ?1")
	Optional<SMSOtp> findByMobileNo(@Param("mobileno") String mobileno);
}
