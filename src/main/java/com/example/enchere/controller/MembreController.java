package com.example.enchere.controller;

import com.example.enchere.entity.Membre;
import com.example.enchere.exception.HttpException;
import com.example.enchere.repository.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membres")
@CrossOrigin
public class MembreController {

    private MembreRepository repository;

    @PostMapping
    public Membre save(@RequestBody Membre membre){
        try {
            return repository.save(membre);
        } catch (DataIntegrityViolationException e){
            throw new HttpException(HttpStatus.BAD_REQUEST, "Username already exist");
        }
    }

    @GetMapping
    public List<Membre> findAll(){
        return repository.findAll();
    }

    @Autowired
    public void setRepository(MembreRepository repository) {
        this.repository = repository;
    }
}
