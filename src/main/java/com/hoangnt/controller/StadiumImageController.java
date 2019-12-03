package com.hoangnt.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.service.StadiumImageService;
import com.hoangnt.utils.UploadImage;

@RestController
public class StadiumImageController {
	@Autowired
	StadiumImageService stadiumImageService;
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PutMapping("/address/image/add/{id}")
	public ResponseEntity<Response<Void>> updateImageAddress(@RequestParam("images") MultipartFile[] files,@PathVariable int id) throws IOException {
		List<String> urls=new ArrayList<String>();
		
		Response<Void> response = new Response<>();
	    ResponseData<Void> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    UploadImage uploadImage=new UploadImage();
	    for(int i=0;i<files.length;i++) {
	    	String nameImage=uploadImage.ramdom() + files[i].getOriginalFilename();
	    	urls.add("http://45.124.94.74:9090/resources/upload-dir/address/" + nameImage);
	    	uploadImage.store(files[i], nameImage,rootLocation);
	    }
	    
		stadiumImageService.addImage(urls, id);
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@DeleteMapping("/address/image/delete/{id}")
	public ResponseEntity<Response<Void>> deleteImageAddress(@PathVariable int id) throws IOException {
		
		Response<Void> response = new Response<>();
	    ResponseData<Void> responseData = new ResponseData<>();
	    response.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    UploadImage uploadImage=new UploadImage();
	    String urlImage=stadiumImageService.getUrlImage(id);
	    uploadImage.deleteImg(urlImage);
	    stadiumImageService.deleteImage(id);
	   
		response.setStatus("OK");
        response.setData(responseData);
		return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
	}
	
	private final Path rootLocation = Paths.get(Paths.get("").toAbsolutePath() + "/src/main/resources/upload-dir/address/");
}
