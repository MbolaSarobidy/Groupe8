package com.example.enchere.controller;

import com.example.enchere.entity.Authenticable;
import com.example.enchere.entity.Membre;
import com.example.enchere.entity.Rencherissement;
import com.example.enchere.service.RencherirService;
import com.example.enchere.service.TokenService;
import com.example.enchere.value_object.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class RencherissementController {

    private RencherirService service;
    private TokenService tokenService;

    @PostMapping("/membres/{id}/rencherissements")
    public ResponseEntity<Message> rencherir(@RequestBody Rencherissement rencherissement, HttpServletRequest request, @PathVariable Long id) throws Exception {
        Authenticable authenticable = tokenService.validate(request, id);
        rencherissement.setMembre(new Membre(authenticable.getId()));
        service.save(rencherissement);
        return new ResponseEntity<>(new Message("Bidding successfully"), HttpStatus.CREATED);
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setService(RencherirService service) {
        this.service = service;
    }
}
