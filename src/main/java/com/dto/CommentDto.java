package com.dto;

import com.entities.Post;
import com.entities.User;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommentDto {

	private Long comid;
	@NotEmpty
	private String content;

}
