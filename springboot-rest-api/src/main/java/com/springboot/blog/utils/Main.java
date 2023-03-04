package com.springboot.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Main {
	public static void main(String[] args) {
		PasswordEncoder pass = new BCryptPasswordEncoder();
		System.out.println(pass.encode("nj"));
		System.out.println(pass.encode("admin"));
		System.out.println(pass.encode("user1"));


	}

}
