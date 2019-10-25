package com.hoangnt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.Stadium;

@Transactional
@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Integer>{

}
