package com.example.enchere.service;

import com.example.enchere.dto.EnchereDTO;
import com.example.enchere.entity.Compte;
import com.example.enchere.entity.Historique;
import com.example.enchere.entity.Rencherissement;
import com.example.enchere.exception.EntityNotFoundException;
import com.example.enchere.exception.HttpException;
import com.example.enchere.repository.EnchereRepository;
import com.example.enchere.repository.HistoriqueRepository;
import com.example.enchere.repository.RencherirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RencherirService {

    private RencherirRepository repository;

    private EnchereService enchereService;

    private HistoriqueRepository historiqueRepository;

    public void save(Rencherissement rencherissement) throws Exception {
        EnchereDTO enchereDTO = enchereService.findById(rencherissement.getEnchere().getId());
        if(enchereDTO == null){
            throw new EntityNotFoundException(EnchereDTO.class, rencherissement.getEnchere().getId());
        }
        if(enchereDTO.statut.equals("ended")){
            throw new HttpException(HttpStatus.BAD_REQUEST, "This auction is already ended");
        }
        if(enchereDTO.owner_id.longValue() == rencherissement.getMembre().getId()){
            throw new HttpException(HttpStatus.BAD_REQUEST, "You cannot bid on your own auction");
        }
        if(enchereDTO.prix_courant >= rencherissement.getPrixEnchere()){
            throw new HttpException(HttpStatus.BAD_REQUEST, "Your bid has to more than current bid "+enchereDTO.prix_courant);
        }
        Compte compte = new Compte();
        compte.identificationSoldeActuel(String.valueOf(rencherissement.getMembre().getId()));
        if(rencherissement.getPrixEnchere() > compte.getSolde())
            throw new HttpException(HttpStatus.BAD_REQUEST, "Sorry but your sale is insufficient");

        repository.save(rencherissement);
        Rencherissement rencherissement1 = repository.findById(rencherissement.getId()).orElseThrow();
        Historique historique = new Historique();
        historique.setMembre_id(String.valueOf(rencherissement1.getMembre().getId()));
        historique.setRencherissement(rencherissement1);
        historiqueRepository.save(historique);
    }

    @Autowired
    public void setHistoriqueRepository(HistoriqueRepository historiqueRepository) {
        this.historiqueRepository = historiqueRepository;
    }

    @Autowired
    public void setRepository(RencherirRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setEnchereService(EnchereService enchereService) {
        this.enchereService = enchereService;
    }
}
