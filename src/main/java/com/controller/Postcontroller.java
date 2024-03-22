package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.config.Appconstants;
import com.dto.PostDto;
import com.payloads.ApiResponse;
import com.payloads.PostResponse;
import com.service.FileService;
import com.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog")  
public class Postcontroller {
	
	@Autowired
	private PostService postService;
	
	@Autowired 
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userid}/category/{cid}/createPost")
	public ResponseEntity<PostDto> createPost(@PathVariable("userid") Long userid,@PathVariable("cid") Long cid, @Valid @RequestBody PostDto postDto){
		if(!(userid>0)) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.postService.createPost(userid,cid,postDto),HttpStatus.OK);
	}
	
	//update Post
	@PutMapping("/updatePost/{postid}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable("postid") Long postid){
		if(!(postid>0)) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.postService.updatePost(postid,postDto),HttpStatus.OK);
	}
	
	//get post by id
	@GetMapping("/getPost/{postid}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postid") Long postid){
		if(!(postid >0))return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.postService.getPostById(postid),HttpStatus.OK);
	}
	
	@GetMapping("/getAllPost")
	public ResponseEntity<PostResponse> getAllPost(@RequestParam(name = "pageNumber",defaultValue =Appconstants.PAGE_NUMBER,required = false) Integer pageNumber,
													@RequestParam(name = "pageSize",defaultValue =  Appconstants.PAGE_SIZE,required = false )Integer pageSize,
													@RequestParam(name = "sortBy",defaultValue = Appconstants.SORT_BY,required = false) String sortBy,
													@RequestParam(name = "sortDir",defaultValue = Appconstants.SORT_DIR,required = false) String sortDir){
		return new ResponseEntity<>(this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	@DeleteMapping("/deletePost/{postid}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postid") Long postid){
		if(!(postid >0))return new ResponseEntity<>(new ApiResponse("Please Enter Valid PostId",false),HttpStatus.OK);
		return new ResponseEntity<>(this.postService.deletePost(postid),HttpStatus.OK);
	}
	
	@GetMapping("/user/{userid}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable("userid") Long userid,
													   @RequestParam(name = "pageNumber",defaultValue = Appconstants.PAGE_NUMBER,required = false) Integer pageNumber,
													   @RequestParam(name = "pageSize",defaultValue = Appconstants.PAGE_SIZE,required = false)Integer pageSize,
													   @RequestParam(name = "sortBy",defaultValue = Appconstants.SORT_BY,required = false) String sortBy,
													   @RequestParam(name = "sortDir",defaultValue = Appconstants.SORT_DIR,required = false)String sortDir){
		
		if(!(userid>0))return new ResponseEntity<>(null,HttpStatus.OK);
		return new ResponseEntity<>(this.postService.getPostByUser(userid,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryid}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(@PathVariable("categoryid")Long categoryid,
														  @RequestParam(name = "pageSize",defaultValue = Appconstants.PAGE_SIZE,required = false)Integer pageSize,
														  @RequestParam(name = "pageNumber",defaultValue = Appconstants.PAGE_NUMBER,required = false) Integer pageNumber,
														  @RequestParam(name = "sortBy",defaultValue = Appconstants.SORT_BY,required = false) String sortBy,
														  @RequestParam(name = "sortDir",defaultValue = Appconstants.SORT_DIR,required = false) String sortDir){
		
		if(!(categoryid>0)) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(this.postService.getPostByCategory(categoryid,pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	@PostMapping("/post/{postid}/upload-image")
	public ResponseEntity<ApiResponse> uploadImage(@PathVariable("postid") Long postid,@RequestParam MultipartFile file){
		if(!(postid>0)) return new ResponseEntity<>(new ApiResponse("Please Enter valid postid",false),HttpStatus.BAD_REQUEST);
		
		String imageName = null;
		PostDto postDto = null;
		try {
			 imageName = this.fileService.uploadImage(path,file);
			PostDto postdto = this.postService.getPostById(postid);
			postdto.setImageName(imageName);
			 postDto = this.postService.updatePost(postid, postdto);
		} catch (IOException e) {
			return new ResponseEntity<>(new ApiResponse("Image Uploaded Successfully",true),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse("Image Not Uploaded Successfully",false),HttpStatus.INTERNAL_SERVER_ERROR) ;
	}
	
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE )
	public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getResource(path,imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream()); 
	}
}
