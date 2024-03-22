package com.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dto.PostDto;
import com.payloads.ApiResponse;
import com.payloads.PostResponse;

public interface PostService {

	PostDto createPost(Long userid,Long cid, PostDto postDto);
	
	PostDto updatePost(Long postid, PostDto postDto);
	
	PostDto getPostById(Long postid);
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	ApiResponse deletePost(Long postid);
	
	PostResponse getPostByUser(Long userid, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	PostResponse getPostByCategory(Long categoryid, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

}
