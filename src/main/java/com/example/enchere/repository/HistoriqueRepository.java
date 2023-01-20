package com.example.enchere.repository;

import com.example.enchere.entity.Historique;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HistoriqueRepository extends MongoRepository<Historique, String> {

    @Query(value = "{'membre_id': ?0}")
    List<Historique> findAll(String membre_id);
}
