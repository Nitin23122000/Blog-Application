package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CategoryDto;
import com.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/createCategory")
	//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		return new ResponseEntity<>(this.categoryService.createCategory(categoryDto),HttpStatus.OK);
	}
}
