package com.springboot.blog.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFound;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService{
      @Autowired
	  private PostRepository PostRepo;
      @Autowired
      private CommentRepository CommentRepo;
  	@Autowired
  	private ModelMapper modelmapper;
	
	@Override
	public CommentDto createCommnet(Long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
	    Post post =	PostRepo.findById(postId).orElseThrow(()-> new ResourceNotFound("Post", "id", postId));
	    comment.setPost(post);
	  Comment Savedcomment =  CommentRepo.save(comment);
	    CommentDto SavedDtoComment = mapToDto(Savedcomment);
		return SavedDtoComment;
	}
  private CommentDto mapToDto(Comment comment) {
	  
	  CommentDto commentDto = modelmapper.map(comment, CommentDto.class);
	

	  return commentDto;
  }
  private Comment mapToEntity(CommentDto commentDto) {
	  
	  Comment comment = modelmapper.map(commentDto, Comment.class);
	  return comment;
  }
@Override
public List<CommentDto> getAllComments(long postId) {
	List<Comment> comments =CommentRepo.findByPostId(postId);
	List<CommentDto> responseComments=comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
	return responseComments;
}
@Override
public CommentDto getCommentsById(long postId, long commentId) {
    Post post =	PostRepo.findById(postId).orElseThrow(()-> new ResourceNotFound("Post", "id", postId));
    Comment comment = CommentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFound("Comment", "id", commentId));
    
    if(!comment.getPost().getId().equals(post.getId())){
    	throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comments does not belong to post");
    }
	return mapToDto(comment);
}
@Override
public CommentDto updateComment(long postId, long commentId ,Comment comment) {
    Post post =	PostRepo.findById(postId).orElseThrow(()-> new ResourceNotFound("Post", "id", postId));
    Comment commentResponse = CommentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFound("Comment", "id", commentId));
    
    if(!commentResponse.getPost().getId().equals(post.getId()))
    	throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comments does not belong to post");

    	commentResponse.setName(comment.getName());
    	commentResponse.setEmail(comment.getEmail());
    	commentResponse.setBody(comment.getBody());
    	Comment updatedComment = CommentRepo.save(commentResponse);
    	

	return mapToDto(updatedComment);

}
@Override
public void deleteComment(long postId, long commentId) {
	 Post post =	PostRepo.findById(postId).orElseThrow(()-> new ResourceNotFound("Post", "id", postId));
	    Comment commentResponse = CommentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFound("Comment", "id", commentId));
	    
	    if(!commentResponse.getPost().getId().equals(post.getId()))
	    	throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comments does not belong to post");
	    CommentRepo.delete(commentResponse);
	
}

}
