package com.hoangnt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.Stadium;

@Transactional
@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Integer>{
	
	@Query("SELECT s FROM Stadium s WHERE s.address.id = ?1")
	List<Stadium> getByIdAddress(int idAddress);
}
