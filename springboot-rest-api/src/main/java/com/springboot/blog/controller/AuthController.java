package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.JWTAuthResponse;
import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegistrationDto;
import com.springboot.blog.service.AuthService;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
	
   @Autowired
   private AuthService authservice;
   
   @PostMapping(value = {"/login" , "/signin"})
   public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto logindto) {
	   
	   String token = authservice.login(logindto);
	   JWTAuthResponse jWTAuthResponse = new JWTAuthResponse();
	   jWTAuthResponse.setToken(token);
	   
	return  ResponseEntity.ok(jWTAuthResponse);
	   
   }
   
   @PostMapping(value = {"/signup ","/register"})
   public ResponseEntity<String> register(@RequestBody RegistrationDto registrationDto){
	   String response = authservice.register(registrationDto);
	return new  ResponseEntity<>(response,HttpStatus.CREATED);
	   
   }
}
