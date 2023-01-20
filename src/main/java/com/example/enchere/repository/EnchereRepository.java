package com.example.enchere.repository;

import com.example.enchere.dto.EnchereDTO;
import com.example.enchere.entity.Enchere;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnchereRepository extends JpaRepository<Enchere, Long>, JpaSpecificationExecutor<EnchereDTO> {

    @Query(value = "SELECT * FROM v_encheres", nativeQuery = true)
    public List<EnchereDTO> findAuctionWithCurrentPrice(Specification<EnchereDTO> specification);
    @Query(value = "SELECT * FROM v_encheres WHERE id_enchere = :id", nativeQuery = true)
    public EnchereDTO findAuctionWithCurrentPriceById(@Param("id") Long id);

}
