package com.hoangnt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.StatusShift;

@Transactional
@Repository
public interface StatusShiftRepository extends JpaRepository<StatusShift, Integer>{

}
