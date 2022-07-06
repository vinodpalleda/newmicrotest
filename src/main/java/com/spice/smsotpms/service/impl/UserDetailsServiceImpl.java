package com.spice.smsotpms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spice.smsotpms.entity.User;
import com.spice.smsotpms.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user name not found "+username));
	    return UserDetailsImpl.build(user);
	}
}
