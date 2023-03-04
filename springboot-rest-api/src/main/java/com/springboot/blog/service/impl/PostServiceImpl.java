package com.springboot.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFound;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public PostDto savePost(PostDto postDto) {
		Category category =categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(()->new ResourceNotFound("Category", "id", postDto.getCategoryId()));
		Post post = mapDtoToEntity(postDto);
		post.setCategories(category);
		Post newPost = postRepo.save(post);
		PostDto postDto1 = mapEntityToDto(newPost);

		return postDto1;
	}

	@Override
	public PostResponse getAllPost(int pageNo,int pageSize ,String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize ,sort );
		Page<Post> AllPost = postRepo.findAll(pageable);

		List<Post> AllPosts = AllPost.getContent();
		List<PostDto> PostContent = AllPosts.stream().map(post-> mapEntityToDto(post)).collect(Collectors.toList());
		
        PostResponse  postresponse = new PostResponse();
        postresponse.setContent(PostContent);
        postresponse.setPageNo(AllPost.getTotalPages());
        postresponse.setPageSize(AllPost.getSize());
        postresponse.setTotalElements(AllPost.getNumberOfElements());
        postresponse.setLast(AllPost.isLast());
        return postresponse;
	}

	private PostDto mapEntityToDto(Post post) {
		PostDto postDto = modelmapper.map(post, PostDto.class);
		
		return postDto;
	}

	private Post mapDtoToEntity(PostDto postDto) {
		Post post =modelmapper.map(postDto, Post.class);
		return post;
	}

	@Override
	public PostDto getPostById(Long id) {
		 Post post=postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));
		 PostDto postDto = mapEntityToDto(post);
		return postDto;
	}

	@Override
	public PostDto updatePost(Long id, PostDto postDto) {
		 Post post=postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));
		 Category category =categoryRepository.findById(postDto.getCategoryId())
					.orElseThrow(()->new ResourceNotFound("Category", "id", postDto.getCategoryId()));
		 post.setTitle(postDto.getTitle());
		 post.setCategories(category);
		 post.setDescription(postDto.getDescription());
		 post.setContent(postDto.getContent());
		 Post updatedPost = postRepo.save(post);
		return mapEntityToDto(updatedPost);
	}

	@Override
	public void deleteById(Long id) {
		 Post post=postRepo.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));
		 postRepo.delete(post);
	}

	@Override
	public List<PostDto> getPostsByCategoryId(Long categoryId){return null;} /*{
		Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));

		 List<Post> posts = postRepo.findByCategoryId(categoryId);
					
		 return posts.stream().map((post)-> mapEntityToDto(post))
				 .collect(Collectors.toList());
	}*/

	
}
