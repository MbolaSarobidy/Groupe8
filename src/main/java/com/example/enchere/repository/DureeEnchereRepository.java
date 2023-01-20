package com.example.enchere.repository;

import com.example.enchere.entity.DureeEnchere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DureeEnchereRepository extends JpaRepository<DureeEnchere, Long> {
}
