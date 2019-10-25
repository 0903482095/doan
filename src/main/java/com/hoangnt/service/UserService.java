package com.hoangnt.service;

import com.hoangnt.entity.User;
import com.hoangnt.model.InformationUser;

public interface UserService<T,V> {

	InformationUser getInfoUser(T t);
	
	User addUser(V v);

	InformationUser getById(int id);

	User findByUserName(String username);

}
