package com.example.enchere.repository;

import com.example.enchere.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findTokenByValue(String value);
    void deleteTokenByValue(String value);
}
