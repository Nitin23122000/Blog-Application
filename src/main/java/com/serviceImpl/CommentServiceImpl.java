package com.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.CommentDto;
import com.entities.Comment;
import com.entities.Post;
import com.entities.User;
import com.exceptions.ResourceNotFoundException;
import com.payloads.ApiResponse;
import com.repository.CommentRepository;
import com.repository.PostRepository;
import com.repository.UserRepository;
import com.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	public CommentDto createComment(CommentDto commentDto, Long postid, Long userid) {

		Post post = this.postRepository.findById(postid).orElseThrow(()-> new ResourceNotFoundException("post", "post id", postid));
		User user = this.userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("user", "user id", userid));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = this.commentRepository.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}



	@Override
	public CommentDto updateComment(CommentDto commentDto, Long comid) {

		Comment comment = this.commentRepository.findById(comid).orElseThrow(()-> new ResourceNotFoundException("comment", "comment id", comid));
		
		comment.setContent(commentDto.getContent());
		Comment updateComment = this.commentRepository.save(comment);
		return 	this.modelMapper.map(updateComment, CommentDto.class);
	}



	@Override
	public List<CommentDto> getAllComments() {

		List<Comment> comments = this.commentRepository.findAll();
		List<CommentDto> commentdtos = comments.stream().map((com)-> this.modelMapper.map(com,CommentDto.class)).collect(Collectors.toList());
		
		return commentdtos;
	}



	@Override
	public CommentDto getCommentById(Long comid) {

		Comment comment = this.commentRepository.findById(comid).orElseThrow(()-> new ResourceNotFoundException("comment", "com id", comid));

		return this.modelMapper.map(comment, CommentDto.class);
	}



	@Override
	public ApiResponse deleteComment(Long comid) {
		Comment comment = this.commentRepository.findById(comid).orElseThrow(()-> new ResourceNotFoundException("comment", "comment id", comid));
		this.commentRepository.delete(comment);
		ApiResponse response = new ApiResponse("Comment Deleted Successfully", true);
		return response;
	}

	
}
