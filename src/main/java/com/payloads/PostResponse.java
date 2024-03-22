package com.payloads;

import java.util.List;

import com.dto.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

	private List<PostDto> content;
	private int pageSize;
	private int pageNumber;
	private Long totalElements;
	private int totalPages;
	private Boolean lastpage;
}
