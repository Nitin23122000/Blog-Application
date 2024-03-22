package com.serviceImpl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dto.PostDto;
import com.entities.Category;
import com.entities.Post;
import com.entities.User;
import com.exceptions.ResourceNotFoundException;
import com.payloads.ApiResponse;
import com.payloads.PostResponse;
import com.repository.CategoryRepository;
import com.repository.PostRepository;
import com.repository.UserRepository;
import com.service.PostService;

@Service 
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public PostDto createPost(Long userid,Long cid, PostDto postDto) {

		User user = this.userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("user", "user id", userid));
		Category category = this.categoryRepository.findById(cid).orElseThrow(()-> new ResourceNotFoundException("category", "category id", cid));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setUser(user);
		post.setCategory(category);
		post.setDate(new Date());
		Post savedPost = this.postRepository.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
		
	}

	@Override
	public PostDto updatePost(Long postid, PostDto postDto) {

		Post post = this.postRepository.findById(postid).orElseThrow(()-> new ResourceNotFoundException("post", "post id", postid));
		post.setPosttitle(postDto.getPosttitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepository.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostDto getPostById(Long postid) {

		Post post = this.postRepository.findById(postid).orElseThrow(()-> new ResourceNotFoundException("post", "post id", postid));
		return  this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

	Sort sort = (sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
	Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		 Page<Post> post = this.postRepository.findAll(p);
		 List<Post> Posts = post.getContent();
		List<PostDto> postDto = Posts.stream().map((po)-> this.modelMapper.map(po, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(post.getNumber());
		postResponse.setPageSize(post.getSize());
		postResponse.setTotalPages(post.getTotalPages());
		postResponse.setTotalElements(post.getTotalElements());
		postResponse.setLastpage(post.isLast());
		return postResponse;
	}

	@Override
	public ApiResponse deletePost(Long postid) {

		Post post = this.postRepository.findById(postid).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postid));
		post.setUser(null);
		this.postRepository.delete(post);
		return new ApiResponse("Post Deleted Successfully", true);
	}

	@Override
	public PostResponse getPostByUser(Long userid,Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pg = PageRequest.of(pageNumber, pageSize,sort);

		User user = this.userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userid));
		Page<Post> po = this.postRepository.findByUser(user,pg);
		List<Post> posts = po.getContent();
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(po.getNumber());
		postResponse.setPageSize(po.getSize());
		postResponse.setTotalElements(po.getTotalElements());
		postResponse.setTotalPages(po.getTotalPages());
		postResponse.setLastpage(po.isLast());

		return postResponse;
	}

	@Override
	public PostResponse getPostByCategory(Long categoryid, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
	Sort sort=	(sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
	Pageable of = PageRequest.of(pageNumber, pageSize,sort);
	Category category = this.categoryRepository.findById(categoryid).orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryid));
	Page<Post> pagePost = this.postRepository.findByCategory(category, of);
	List<Post> posts = pagePost.getContent();
	List<PostDto> postDtos = posts.stream().map((p)-> this.modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
	
	PostResponse postResponse = new PostResponse();
	postResponse.setContent(postDtos);
	postResponse.setPageNumber(pagePost.getNumber());
	postResponse.setPageSize(pagePost.getSize());
	postResponse.setTotalElements(pagePost.getTotalElements());
	postResponse.setTotalPages(pagePost.getTotalPages());
	postResponse.setLastpage(pagePost.isLast());
		return postResponse;
	}

	

	
	
}
