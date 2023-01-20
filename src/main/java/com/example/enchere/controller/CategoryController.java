package com.example.enchere.controller;

import com.example.enchere.entity.Categorie;
import com.example.enchere.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {

    private CategorieRepository repository;

    @GetMapping
    public List<Categorie> listAll(){
        return repository.findAll();
    }

    @Autowired
    public void setRepository(CategorieRepository repository) {
        this.repository = repository;
    }
}
