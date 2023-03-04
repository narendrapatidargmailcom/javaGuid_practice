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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	  @Autowired
	  private  CategoryService categoryservice;
	
	    @PostMapping
		@PreAuthorize("hasRole('ADMIN')")
       public ResponseEntity<CategoryDto>  addCategory(@RequestBody CategoryDto categoryDto){
          CategoryDto responseCayegoryDto = categoryservice.addCategory(categoryDto);
		  return new ResponseEntity<>(responseCayegoryDto , HttpStatus.CREATED);
        }
	    
	    @GetMapping
        public ResponseEntity<List<CategoryDto>>  getCategories(){
          List<CategoryDto> ListresponseCayegoryDto = categoryservice.getAllCategory();
		  return new ResponseEntity<>(ListresponseCayegoryDto , HttpStatus.OK);
        }
	    
	    @GetMapping({"/{id}"})
        public ResponseEntity<CategoryDto> getCategories(@PathVariable long id){
          CategoryDto responsecategoryDto  = categoryservice.getCategory(id);
		  return new ResponseEntity<>(responsecategoryDto , HttpStatus.OK);
        }
	    
	    @PutMapping({"/{id}"})
        public ResponseEntity<CategoryDto> updateCategories(
        		  @PathVariable long id,
        		   @RequestBody  CategoryDto CategoryDto){
          CategoryDto responsecategoryDto  = categoryservice.updateCategory(id, CategoryDto);
		  return new ResponseEntity<>(responsecategoryDto , HttpStatus.OK);
        }
	    
	    @DeleteMapping({"/{id}"})
        public ResponseEntity<String> deleteCategories(@PathVariable long id){
           String res= categoryservice.deleteCategory(id);
		  return new ResponseEntity<>(res , HttpStatus.OK);
        }
}
