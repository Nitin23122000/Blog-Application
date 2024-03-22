package com.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.entities.Category;
import com.entities.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	
	private Long pid;
	
	@NotEmpty
	@Size(min = 3, max = 6, message = "posttitle must contain min 3 chars and max 6 chars")
	private String posttitle;
	
	@NotEmpty
	private String content;
	
	private String imageName;
	
	private Date date;
	
	private UserDto user;
	
	private CategoryDto category;
	
	private Set<CommentDto> comment = new HashSet<>();
}
