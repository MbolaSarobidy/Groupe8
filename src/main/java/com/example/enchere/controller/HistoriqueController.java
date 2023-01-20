package com.example.enchere.controller;

import com.example.enchere.entity.Historique;
import com.example.enchere.repository.HistoriqueRepository;
import com.example.enchere.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class HistoriqueController {

    private HistoriqueRepository repository;
    private TokenService tokenService;

    @GetMapping("/membres/{id}/historiques")
    public List<Historique> listAll(@PathVariable Long id, HttpServletRequest request){
        tokenService.validate(request, id);
        return repository.findAll(String.valueOf(id));
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setRepository(HistoriqueRepository repository) {
        this.repository = repository;
    }
}
