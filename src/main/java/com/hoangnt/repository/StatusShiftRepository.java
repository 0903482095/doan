package com.hoangnt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.StatusShift;

@Transactional
@Repository
public interface StatusShiftRepository extends JpaRepository<StatusShift, Integer>{
	@Query("SELECT s FROM StatusShift s WHERE s.date = ?1")
	List<StatusShift> getAllStatusShiftByDate(String date);
	
	@Query("SELECT s FROM StatusShift s WHERE s.shift.id = ?1")
	StatusShift getStatusShiftByIdShift(int idShift);
}
