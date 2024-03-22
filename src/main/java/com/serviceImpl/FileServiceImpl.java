package com.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.payloads.ApiResponse;
import com.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Service
public class FileServiceImpl implements FileService {

	public String uploadImage(String path, MultipartFile file) throws IOException {

		//Getting the file name
		String fileName = file.getOriginalFilename();
		
		//creating the unique file name
		String uuid = UUID.randomUUID().toString();
		String uniquefileName = uuid.concat(fileName.substring(fileName.lastIndexOf(".")));
		
		//creating the full Path
		
		String FullPath = path+File.separator+uniquefileName;
		
		File f = new File(path);
		
		if(!f.exists()) {
			
			f.mkdir();
		}
		
		
		//Copy the file
		Files.copy(file.getInputStream(), Paths.get(FullPath));
		return uniquefileName;
	}

	@Override
	public InputStream getResource(String path, String imageName) throws FileNotFoundException {

	String fullPath =	path+File.separator+imageName;
	FileInputStream inputStream = new FileInputStream(fullPath);
		return inputStream;
	}
}
