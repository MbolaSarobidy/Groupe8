package com.example.enchere.entity;

import javax.persistence.*;

@Entity
public class Membre extends Authenticable {

    public Membre(Long id) {
        setId(id);
    }

    public Membre(){}
}
