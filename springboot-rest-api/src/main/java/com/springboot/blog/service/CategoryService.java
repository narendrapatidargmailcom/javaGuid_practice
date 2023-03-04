package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.dto.CategoryDto;

public interface CategoryService {
     CategoryDto addCategory(CategoryDto categoryDto);
     
     List<CategoryDto> getAllCategory();
     
     CategoryDto getCategory(long id);
     
     CategoryDto updateCategory(long id,CategoryDto categoryDto);
     
     String deleteCategory(long id);


     
     

     
}
