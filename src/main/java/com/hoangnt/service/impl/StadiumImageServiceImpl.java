package com.hoangnt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoangnt.entity.Address;
import com.hoangnt.entity.StadiumImage;
import com.hoangnt.repository.StadiumImageRepository;
import com.hoangnt.service.StadiumImageService;

@Service
public class StadiumImageServiceImpl implements StadiumImageService{
	
	@Autowired
	StadiumImageRepository stadiumImageRepository;

	@Override
	public void addImage(List<String> stadiumImageDTO, int idAddress) {
		List<StadiumImage> stadiumImages = new ArrayList<StadiumImage>();
		stadiumImageDTO.forEach(url -> {
			StadiumImage stadiumImage = new StadiumImage();
			stadiumImage.setUrlImage(url);
			stadiumImage.setStadiumImage(new Address(idAddress));
			stadiumImages.add(stadiumImage);
			
		});
		stadiumImageRepository.saveAll(stadiumImages);
	}

	@Override
	public void deleteImage(int id,int idUser) {
		stadiumImageRepository.deleteById(id);
	}

	@Override
	public String getUrlImage(int id) {
		
		return stadiumImageRepository.findById(id).get().getUrlImage();
	}
	
	

}
