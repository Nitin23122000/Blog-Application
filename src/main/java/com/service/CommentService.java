package com.service;

import java.util.List;

import com.dto.CommentDto;
import com.payloads.ApiResponse;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Long postid, Long userid);

	CommentDto updateComment(CommentDto commentDto, Long comid);

	List<CommentDto> getAllComments();

	CommentDto getCommentById(Long comid);

	ApiResponse deleteComment(Long comid);

}
