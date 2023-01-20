package com.example.enchere.dto;

import java.math.BigInteger;
import java.sql.Timestamp;


//Without encapsulation because of a simple read-only transferer dto

public class EnchereDTO {
    public BigInteger id_enchere;
    public Timestamp date_debut,date_fin;
    public String description;
    public double prix_minimum;
    public String categorie;
    public double prix_courant;
    public BigInteger categorie_id;
    public BigInteger owner_id, membre_id;
    public String owner;
    public String statut;

}
