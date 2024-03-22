package com.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.CategoryDto;
import com.entities.Category;
import com.repository.CategoryRepository;
import com.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
	
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepository.save(category);
		CategoryDto catDto = this.modelMapper.map(savedCategory, CategoryDto.class);
		return catDto;
	}

}
