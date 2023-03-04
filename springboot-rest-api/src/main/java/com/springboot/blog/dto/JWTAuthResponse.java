package com.springboot.blog.dto;

import lombok.Data;
@Data
public class JWTAuthResponse {
	
	
	private String token;
	private String tokenType = "Bearer";

}
