package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
	private Long id;
	@NotEmpty(message = "Name should be not null and Enpty")
	private String name;
	@NotEmpty(message = "Email should not be null and Empty")
	@Email
	private String email;
	@Size(min = 10,message = "body should be 10 character long")
	private String body;
}
