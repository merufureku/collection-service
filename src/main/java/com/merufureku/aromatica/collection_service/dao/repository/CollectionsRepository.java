package com.merufureku.aromatica.collection_service.dao.repository;

import com.merufureku.aromatica.collection_service.dao.entity.Collections;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionsRepository extends JpaRepository<Collections, Integer> {

    void deleteByUserIdAndFragranceId(Integer userId, Long fragranceId);

    List<Collections> findByUserId(Integer userId);

    boolean existsByUserIdAndFragranceId(Integer userId, Long fragranceId);

}
