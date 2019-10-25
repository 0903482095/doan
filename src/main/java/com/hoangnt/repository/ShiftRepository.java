package com.hoangnt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.Shift;

@Transactional
@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer>{

}
