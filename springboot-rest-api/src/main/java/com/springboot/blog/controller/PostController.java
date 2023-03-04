package com.springboot.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

		@Autowired
		private PostService postServ;
		
	
		@PreAuthorize("hasRole('ADMIN')")
		@PostMapping
		public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto post) {
			PostDto PostRes = postServ.savePost(post);
			return new ResponseEntity<>(PostRes, HttpStatus.CREATED);
	
		}
	
		@GetMapping
		public PostResponse getAllPost(
				@RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
				@RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
				@RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
				@RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DRECTION, required = false) String sortDir) {
			PostResponse AllPost = postServ.getAllPost(pageNo, pageSize, sortBy, sortDir);
			return AllPost;
		}
	
		@GetMapping("/{id}")
		public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
			PostDto postDto = postServ.getPostById(id);
			return new ResponseEntity<>(postDto, HttpStatus.OK);
		}
	
		@PreAuthorize("hasRole('ADMIN')")
		@PutMapping("/{id}")
		public ResponseEntity<PostDto> updatePost(@Valid @PathVariable Long id, @RequestBody PostDto postDto) {
	
			PostDto resPostDto = postServ.updatePost(id, postDto);
			return new ResponseEntity<>(resPostDto, HttpStatus.OK);
		}
	
		@DeleteMapping("/{id}")
		@PreAuthorize("hasRole('ADMIN')") 
		public ResponseEntity<String> deletePost(@PathVariable Long id) {
			postServ.deleteById(id);
			return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);

		}
		@GetMapping("/category/{id}")
		public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable Long id) {
			List<PostDto> postDtos = postServ.getPostsByCategoryId(id);
			return new ResponseEntity<>(postDtos, HttpStatus.OK);
		}
		

}
