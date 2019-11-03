package com.hoangnt.service;

import java.util.List;

public interface StadiumImageService {
	void addImage(List<String> stadiumImageDTO, int idAddress);
	
	void deleteImage(int id);
	
	String getUrlImage(int id);
}
