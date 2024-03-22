package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CommentDto;
import com.payloads.ApiResponse;
import com.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@PostMapping("/user/{userid}/post/{postid}/comment")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,@PathVariable("postid") Long postid,@PathVariable("userid") Long userid ){
		if(!(postid >0))return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.commentService.createComment(commentDto,postid,userid),HttpStatus.OK);
	}
	
	@PutMapping("/comment/{comid}/update-comment")
	public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto,@PathVariable("comid") Long comid){
		if(!(comid>0)) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.commentService.updateComment(commentDto,comid),HttpStatus.OK);
	}
	
	@GetMapping("/comment/getAllComments")
	public ResponseEntity<List<CommentDto>> getAllComments(){
		return new ResponseEntity<>(this.commentService.getAllComments(),HttpStatus.OK);
	}
	
	@GetMapping("/comment/getCommentById/{comid}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable("comid") Long comid){
		if(!(comid>0))return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.commentService.getCommentById(comid),HttpStatus.OK);
	}
	
	@DeleteMapping("/comment/deleteComment/{comid}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable("comid") Long comid){
		return new ResponseEntity<>(this.commentService.deleteComment(comid),HttpStatus.OK);
	}
}
