package com.hoangnt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.City;

@Transactional
@Repository
public interface CityRepository extends JpaRepository<City, String> {

}
