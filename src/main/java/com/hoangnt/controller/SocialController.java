package com.hoangnt.controller;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hoangnt.config.TokenProvider;
import com.hoangnt.entity.User;
import com.hoangnt.model.InformationUser;
import com.hoangnt.model.request.RequestSocial;
import com.hoangnt.model.response.Response;
import com.hoangnt.model.response.ResponseData;
import com.hoangnt.repository.SocialRepository;
import com.hoangnt.repository.UserRepository;
import com.hoangnt.service.UserService;

@Transactional
@RestController
public class SocialController {
	@Qualifier("user")
	@Autowired
	UserService socialService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	SocialRepository socialRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	EntityManager em;

	@PostMapping("social/login")
	public ResponseEntity<?> addUser(@RequestBody RequestSocial requestSocial) {
		User user = new User();
		if (null == socialRepository.findByProviderUserId(requestSocial.getProviderUserId())) {
			user = socialService.addUser(requestSocial);
			em.refresh(user);
		}
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(requestSocial.getProviderUserId(), "social"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		InformationUser informationUser = socialService.getInfoUser(requestSocial);
		Response<InformationUser> response = new Response<>();
		ResponseData<InformationUser> responseData = new ResponseData<>();
		responseData.setAccountInfo(informationUser);
		responseData.setIdToken(tokenProvider.generateToken(authentication));
		response.setStatus("OK");
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setData(responseData);

		return new ResponseEntity<Response<InformationUser>>(response, HttpStatus.OK);
	}
}
