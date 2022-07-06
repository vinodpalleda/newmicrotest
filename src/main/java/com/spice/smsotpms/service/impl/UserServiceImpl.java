package com.spice.smsotpms.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spice.smsotpms.entity.User;
import com.spice.smsotpms.error.UserDetailNotFoundException;
import com.spice.smsotpms.repository.UserRepository;
import com.spice.smsotpms.service.UserService;
import com.spice.smsotpms.utility.GenericResponse;
import com.spice.smsotpms.utility.Library;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
   	
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepo.findAll().iterator().forEachRemaining(list::add);
        return userRepo.findAll();
    }

    public Optional<User> findOne(String mobileNo) {
        return userRepo.findByUsername(mobileNo);
    }

	@Override
	public GenericResponse getUsers() throws Exception {
		List<User> users = userRepo.findAll();
		return Library.getSuccess(users, "Users details");
	}

	@Override
	public GenericResponse userDetails(String userName) throws Exception {
		User user = userRepo.findByUsername(userName).orElseThrow(() -> new UserDetailNotFoundException(userName));
		user.setPassword(null);
		user.setRoles(null);
		return Library.getSuccess(user, String.format(userName," details"));
	}	
	
    
}
