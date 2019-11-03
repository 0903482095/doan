package com.hoangnt.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hoangnt.config.TokenProvider;
import com.hoangnt.entity.User;
import com.hoangnt.model.AccountDTO;
import com.hoangnt.model.InformationUser;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.repository.UserRepository;
import com.hoangnt.service.UserService;
import com.hoangnt.utils.UploadImage;

@Transactional
@RestController
public class UserController {
	@Qualifier("manager")
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	EntityManager em;

	@PostMapping("users/register")
	public ResponseEntity<Response<InformationUser>> addUser(@RequestBody InformationUser informationUser) {
		Response<InformationUser> response = new Response<>();
		ResponseData<InformationUser> responseData = new ResponseData<>();

		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		if (null != userRepository.findByUsername(informationUser.getEmail())) {
			responseData.setAccountInfo(informationUser);
			response.setStatus("Email in this account already exist");
			response.setData(responseData);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			User user = new User();
			user = userService.addUser(informationUser);
			em.refresh(user);
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(informationUser.getEmail(), informationUser.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			informationUser.setId(user.getId());
			informationUser.setPassword(null);
			responseData.setAccountInfo(informationUser);
			responseData.setIdToken(tokenProvider.generateToken(authentication));
			response.setStatus("OK");
			response.setData(responseData);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("users/login")
	public ResponseEntity<Response<InformationUser>> login(@RequestBody AccountDTO accountDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(accountDTO.getUsername(), accountDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		InformationUser informationUser = userService.getInfoUser(accountDTO);
		Response<InformationUser> response = new Response<>();
		ResponseData<InformationUser> responseData = new ResponseData<>();
		informationUser.setPassword(null);
		responseData.setAccountInfo(informationUser);
		responseData.setIdToken(tokenProvider.generateToken(authentication));
		response.setStatus("OK");
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setData(responseData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@GetMapping("/users/info")
	public ResponseEntity<Response<InformationUser>> getInfoUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();

		InformationUser informationUser = userService.getById(id);
		informationUser.setPassword("");
		Response<InformationUser> response = new Response<>();
		ResponseData<InformationUser> responseData = new ResponseData<>();
		responseData.setAccountInfo(informationUser);
		response.setStatus("OK");
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setData(responseData);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PutMapping("/users/update")
	public ResponseEntity<Response<InformationUser>> updateInfoUser(@RequestBody InformationUser informationUser)
			throws IOException {

		Response<InformationUser> response = new Response<>();
		ResponseData<InformationUser> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();
		informationUser.setId(id);
		User user = new User();
		user = userService.addUser(informationUser);
		responseData.setAccountInfo(informationUser);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	@PutMapping("/users/update/image")
	public ResponseEntity<Response<InformationUser>> updateImageUser(@RequestParam("file") MultipartFile file)
			throws IOException {
		InformationUser informationUser = new InformationUser();

		Response<InformationUser> response = new Response<>();
		ResponseData<InformationUser> responseData = new ResponseData<>();
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		int id = userService.findByUserName(authentication.getName()).getId();
		informationUser.setId(id);

		UploadImage uploadImage = new UploadImage();
		String nameImage = uploadImage.ramdom() + file.getOriginalFilename();
		informationUser.setImageURL("http://vuonxa.com:9090/resources/upload-dir/user/" + nameImage);

		uploadImage.store(file, nameImage, rootLocation);

		User user = new User();
		user = userService.addUser(informationUser);
		responseData.setAccountInfo(informationUser);
		response.setStatus("OK");
		response.setData(responseData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// upload file
	private final Path rootLocation = Paths
			.get(Paths.get("").toAbsolutePath() + "/src/main/resources/upload-dir/user/");

}
