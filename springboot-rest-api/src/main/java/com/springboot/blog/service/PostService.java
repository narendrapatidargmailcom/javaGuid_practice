package com.springboot.blog.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;

@Service
public interface PostService {
   PostDto savePost(PostDto postDto);
   PostResponse getAllPost(int pageNo , int pageSize , String sortBy, String sortDir);
   PostDto getPostById(Long id);
   PostDto updatePost(Long id ,PostDto postDto );
   void deleteById(Long id);
   List<PostDto> getPostsByCategoryId(Long categoryId); 
}
