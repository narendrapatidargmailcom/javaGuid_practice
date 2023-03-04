package com.springboot.blog.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegistrationDto;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userrepo;
  @Autowired
  private RoleRepository rolerepo;
  @Autowired
  private PasswordEncoder passwordencoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
  
	@Override
	public String login(LoginDto logindto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logindto.getUsernameOrEmail(),
				logindto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.genrateToken(authentication);
		
		return token;
	}

	@Override
	public String register(RegistrationDto registrationDto) {
		if(userrepo.existsByUsername(registrationDto.getUsername()))
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username is alredy there");
		
		if(userrepo.existsByEmail(registrationDto.getEmail()))
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "email is alredy there");
		  User user = new User();
		  user.setName(registrationDto.getName());
		  user.setEmail(registrationDto.getEmail());
		  user.setUsername(registrationDto.getUsername());
		  user.setPassword(passwordencoder.encode(registrationDto.getPassword()));
		  
		  Set<Role> roles = new HashSet<>();
		  Role role = rolerepo.findByName("ROLE_USER").get();
		  roles.add(role);
		  user.setRoles(roles);
		  userrepo.save(user);
		return "User Register Successfully!!!";
	}

}
