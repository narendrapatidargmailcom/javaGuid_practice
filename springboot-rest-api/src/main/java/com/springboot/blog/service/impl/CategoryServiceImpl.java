package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFound;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private CategoryRepository categoryrepository;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		     Category category = DtoToEntity(categoryDto);
		     Category Savedcategory= categoryrepository.save(category);
		     
		return EntityToDto(Savedcategory);
	}
   
	
	 protected Category DtoToEntity(CategoryDto categoryDto) {
		 Category category = modelmapper.map(categoryDto, Category.class) ;
		return category;
		 
	 }  
	 protected CategoryDto EntityToDto(Category category) {
		 CategoryDto categoryDto = modelmapper.map(category, CategoryDto.class) ;
		return categoryDto;
		 
	 }


	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category>  categories= categoryrepository.findAll();
		List<CategoryDto> categoriesDto =categories.stream().map((category)-> EntityToDto(category)).collect(Collectors.toList());
		return categoriesDto;
	}


	@Override
	public CategoryDto getCategory(long id) {
		Category category=categoryrepository.findById(id).orElseThrow(()-> new ResourceNotFound("category", "id", id));
		return EntityToDto(category);
	}


	@Override
	public CategoryDto updateCategory(long id, CategoryDto requestcategoryDto) {
		CategoryDto responsecategorydto = getCategory(id);
		responsecategorydto.setId(requestcategoryDto.getId());
		responsecategorydto.setName(requestcategoryDto.getName());
		responsecategorydto.setDescription(requestcategoryDto.getDescription());
		 Category  category = DtoToEntity(responsecategorydto);
		 Category updatedcategory =categoryrepository.save(category);
		return EntityToDto(updatedcategory);
	}


	@Override
	public String deleteCategory(long id) {
		 CategoryDto categorydto = getCategory(id);
		 Category category =  DtoToEntity(categorydto);
		   categoryrepository.delete(category);
		return "Category deleted successfully";
	}  
}
