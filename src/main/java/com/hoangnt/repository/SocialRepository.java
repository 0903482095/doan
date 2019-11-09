package com.hoangnt.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hoangnt.entity.SocialInfo;
@Transactional
@Repository
public interface SocialRepository extends JpaRepository<SocialInfo, Integer> {
    @Query("SELECT s FROM SocialInfo s WHERE s.providerUserId = ?1")
    SocialInfo findByProviderUserId(String providerUserId);
}
