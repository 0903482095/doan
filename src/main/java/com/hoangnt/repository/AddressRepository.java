package com.hoangnt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.Address;

@Transactional
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
	
	@Query("SELECT a FROM Address a WHERE a.user.id = ?1")
	List<Address> getByIdUser(int idUser);
	
}
