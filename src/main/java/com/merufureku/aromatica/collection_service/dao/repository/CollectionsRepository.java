package com.merufureku.aromatica.collection_service.dao.repository;

import com.merufureku.aromatica.collection_service.dao.entity.Collections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CollectionsRepository extends JpaRepository<Collections, Integer> {

    void deleteByUserIdAndFragranceId(Integer userId, Long fragranceId);

    List<Collections> findByUserId(Integer userId);

    boolean existsByUserIdAndFragranceId(Integer userId, Long fragranceId);

    @Query("SELECT c FROM Collections c " +
           "WHERE c.fragranceId IN :fragranceIds " +
           "AND (:userId IS NULL OR :userId = 0 OR c.userId <> :userId)")
    List<Collections> findAllByFragranceIdInExcludingUser(@Param("fragranceIds") Set<Long> fragranceIds,
                                                          @Param("userId") Integer userId);

    List<Collections> findAllByUserIdIn(Set<Integer> userIds);
}
