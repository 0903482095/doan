package com.hoangnt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.StadiumImage;

@Transactional
@Repository
public interface StadiumImageRepository extends JpaRepository<StadiumImage, Integer>{

}
