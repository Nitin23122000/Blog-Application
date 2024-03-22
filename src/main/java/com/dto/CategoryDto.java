package com.dto;

import java.util.List;

import com.entities.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryDto {

	
	private Long cid;
	
	 @NotEmpty
	private String categoryName;
	
	
}
