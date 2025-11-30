package com.merufureku.aromatica.collection_service.dao.repository;

import com.merufureku.aromatica.collection_service.dao.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByUserIdAndJtiAndType(Integer userId, String token, String type);

}
