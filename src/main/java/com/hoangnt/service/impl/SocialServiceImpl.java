package com.hoangnt.service.impl;

import javax.transaction.Transactional;

import com.hoangnt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.Account;
import com.hoangnt.entity.Role;
import com.hoangnt.entity.SocialInfo;
import com.hoangnt.entity.User;
import com.hoangnt.model.InformationUser;
import com.hoangnt.model.request.RequestSocial;
import com.hoangnt.repository.SocialRepository;
import com.hoangnt.repository.UserRepository;

@Transactional
@Service("user")
public class SocialServiceImpl implements UserService<RequestSocial,RequestSocial> {

	@Autowired
	SocialRepository socialRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public InformationUser getInfoUser(RequestSocial requestSocial) {
		Account account = socialRepository.findByProviderUserId(requestSocial.getProviderUserId()).getAccount();
		User user = account.getUser();
		InformationUser informationUser = new InformationUser();
		informationUser.setId(user.getId());
		informationUser.setEmail(user.getEmail());
		informationUser.setFullName(user.getFullName());
		informationUser.setPhone(user.getPhone());
		informationUser.setGender(user.getGender());
		informationUser.setImageURL(user.getImageURL());
		return informationUser;
	}

	@Override
	public User addUser(RequestSocial requestSocial) {
		SocialInfo socialInfo = new SocialInfo();
		socialInfo.setProviderUserId(requestSocial.getProviderUserId());
		socialInfo.setProviderId(requestSocial.getProviderId());

		Account account = new Account();
		account.setSocialInfo(socialInfo);

		User user = new User();
		user.setEmail(requestSocial.getEmail());
		user.setAccount(account);
		user.setFullName(requestSocial.getDisplayName());
		user.setImageURL(requestSocial.getImageURL());
		user.setRole(new Role(3));
		return userRepository.save(user);
	}

	@Override
	public InformationUser getById(int id) {
		return null;
	}

	@Override
	public User findByUserName(String username) {
		return socialRepository.findByProviderUserId(username).getAccount().getUser();
	}

}
