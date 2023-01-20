package com.example.enchere.service;

import com.example.enchere.dto.EnchereDTO;
import com.example.enchere.entity.DureeEnchere;
import com.example.enchere.entity.Enchere;
import com.example.enchere.exception.EntityNotFoundException;
import com.example.enchere.exception.HttpException;
import com.example.enchere.repository.DureeEnchereRepository;
import com.example.enchere.repository.EnchereDTORepository;
import com.example.enchere.repository.EnchereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EnchereService {

    private EnchereRepository repository;

    private EnchereDTORepository dtoRepository;

    private EnchereRepository enchereRepository;

    private DureeEnchereRepository dureeEnchereRepository;

    @Autowired
    public void setEnchereRepository(EnchereRepository enchereRepository) {
        this.enchereRepository = enchereRepository;
    }

    @Autowired
    public void setRepository(EnchereRepository repository) {
        this.repository = repository;
    }

    public Enchere save(Enchere enchere) {
        DureeEnchere dureeEnchere = dureeEnchereRepository.findById(1L).orElseThrow();
        int second = enchere.getDuree().toSeconde();
        if(second > dureeEnchere.getMax() || second < dureeEnchere.getMin())
            throw new HttpException(HttpStatus.BAD_REQUEST, "The duration of the auction must be between of "+ TimeUnit.SECONDS.toHours(dureeEnchere.getMin()) + " hour and "+TimeUnit.SECONDS.toHours(dureeEnchere.getMax())+" hour");
        return repository.save(enchere);
    }

    public List<EnchereDTO> findAllWithCurrentPrice(String statut, Double prix, Integer categorie) {
        StringBuilder criteriaBuilder = new StringBuilder("WHERE 1=1");
        if(statut != null){
            criteriaBuilder.append(" AND statut=").append("'"+statut+"'");
        }
        if(prix != null){
            criteriaBuilder.append(" AND prix_courant=").append(prix);
        }
        if(categorie != null){
            criteriaBuilder.append(" AND categorie_id=").append(categorie);
        }
        return dtoRepository.findAuctionWithCurrentPrice(criteriaBuilder.toString());
    }

    public EnchereDTO findById(Long id_enchere){
        List<EnchereDTO> enchereDTOS = dtoRepository.findAuctionWithCurrentPrice("where id_enchere="+id_enchere);
        if(enchereDTOS.size() == 0){
            throw new EntityNotFoundException(Enchere.class, id_enchere);
        }
        return enchereDTOS.get(0);
    }

    @Autowired
    public void setDureeEnchereRepository(DureeEnchereRepository dureeEnchereRepository) {
        this.dureeEnchereRepository = dureeEnchereRepository;
    }

    @Autowired
    public void setDtoRepository(EnchereDTORepository dtoRepository) {
        this.dtoRepository = dtoRepository;
    }

    public List<EnchereDTO> findAllByOwnerId(Long id) {
        return dtoRepository.findAuctionWithCurrentPrice("WHERE owner_id = "+id);
    }
}
