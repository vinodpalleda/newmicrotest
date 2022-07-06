package com.spice.smsotpms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spice.smsotpms.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	@Query("select s from User s where s.moblieno = ?1")
	Optional<User> findByMobileNo(@Param("mobileno") String mobileNo);
	
	Optional<User> findByUsername(String username);
}
