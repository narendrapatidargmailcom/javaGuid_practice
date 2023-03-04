package com.springboot.blog.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

	private Long id;
	@NotEmpty
	@Size(min = 2,message = "Post title should have atleast 2 character")
	private String title;
	@NotEmpty
	@Size(min = 10,message = "Post Description should have atleast 10 character")
	private String description;
	@NotEmpty
	private String content;
    Set<CommentDto> comments;
    
    private Long categoryId;
}
