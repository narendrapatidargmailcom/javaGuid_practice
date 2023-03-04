package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
   private CommentService commentService;
	
	@PostMapping("posts/{postId}/comments")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CommentDto> createComment(@PathVariable long postId ,@Valid @RequestBody CommentDto commentDto) {
		CommentDto commentDtoRes =commentService.createCommnet(postId, commentDto);
		return new ResponseEntity<>(commentDtoRes, HttpStatus.CREATED);
	}
	@GetMapping("posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getAllComents(@PathVariable long postId){
		List<CommentDto> commentDto = commentService.getAllComments(postId);
		return new ResponseEntity<>(commentDto ,HttpStatus.OK);
		
	}
	
	@GetMapping("posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId ,@PathVariable long id){
		CommentDto commentDto = commentService.getCommentsById(postId,id);
		return new ResponseEntity<>(commentDto ,HttpStatus.OK);
		
	}
	@PutMapping("posts/{postId}/comments/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CommentDto> updateCommentById(@PathVariable long postId ,@PathVariable long id,@Valid @RequestBody Comment comment){
		CommentDto commentDto = commentService.updateComment(postId, id, comment);
		return new ResponseEntity<>(commentDto ,HttpStatus.OK);
		
	}
	@DeleteMapping("posts/{postId}/comments/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteComment(@PathVariable long postId ,@PathVariable long id){
		  commentService.deleteComment(postId, id);
		return new ResponseEntity<>("comment deleted successfully" ,HttpStatus.OK);
		
	}
	
}
