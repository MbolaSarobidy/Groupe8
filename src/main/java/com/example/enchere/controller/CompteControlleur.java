package com.example.enchere.controller;

import com.example.enchere.entity.Authenticable;
import com.example.enchere.entity.Compte;
import com.example.enchere.entity.Membre;
import com.example.enchere.service.TokenService;
import com.example.enchere.value_object.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class CompteControlleur {

    private TokenService tokenService;

    @PostMapping("membres/{id}/comptes")
    public ResponseEntity<Message> save(@RequestBody Compte compte, HttpServletRequest request, @PathVariable Long id) throws Exception {
        Authenticable authenticable = tokenService.validate(request, id);
        compte.setMembre(new Membre(authenticable.getId()));
        compte.rechargerCompte();
        return new ResponseEntity<>(new Message("Recharged"), HttpStatus.CREATED);
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }
}
