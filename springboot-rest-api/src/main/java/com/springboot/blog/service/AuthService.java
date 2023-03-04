package com.springboot.blog.service;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegistrationDto;

public interface AuthService {
	
     String login(LoginDto logindto);
     String register(RegistrationDto registrationDto);
     
     
}
