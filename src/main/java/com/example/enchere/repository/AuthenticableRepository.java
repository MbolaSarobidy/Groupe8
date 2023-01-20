package com.example.enchere.repository;

import com.example.enchere.entity.Authenticable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticableRepository extends JpaRepository<Authenticable, Long> {
    public Authenticable findAuthenticableByUsernameAndPassword(String username, String password);
}
