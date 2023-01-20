package com.example.enchere.repository;

import com.example.enchere.entity.Rencherissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RencherirRepository extends JpaRepository<Rencherissement, Long> {
}
