package com.example.enchere.repository;

import com.example.enchere.dto.EnchereDTO;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EnchereDTORepository {

    private EntityManager entityManager;

    public List<EnchereDTO> findAuctionWithCurrentPrice(String criteria){
        return entityManager.createNativeQuery("SELECT * FROM v_encheres "+criteria).
                unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.aliasToBean(EnchereDTO.class))
                .getResultList();
    }

    @Autowired
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
