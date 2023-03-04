package com.springboot.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;

@Service
public interface CommentService {
	
   CommentDto  createCommnet(Long postId , CommentDto commentDto);
   
   List<CommentDto> getAllComments(long postId); 
   
   CommentDto getCommentsById(long postId,long commentId);
   
   CommentDto updateComment(long postId,long commentId,Comment comment);

   void deleteComment(long postId,long commentId);

}
