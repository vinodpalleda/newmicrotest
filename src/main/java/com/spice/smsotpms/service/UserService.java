package com.spice.smsotpms.service;

import java.util.List;
import java.util.Optional;

import com.spice.smsotpms.entity.User;
import com.spice.smsotpms.utility.GenericResponse;


public interface UserService {
    List<User> findAll();
    Optional<User> findOne(String username);
	GenericResponse getUsers() throws Exception;
	GenericResponse userDetails(String userName) throws Exception;
}
