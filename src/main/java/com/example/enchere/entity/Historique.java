package com.example.enchere.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("Historique")
public class Historique {

    @Id
    private String id;

    public Historique() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Rencherissement rencherissement;
    private String membre_id;

    public Historique(String id, Rencherissement rencherissement, String membre_id) {
        this.id = id;
        this.rencherissement = rencherissement;
        this.membre_id = membre_id;
    }

    public Rencherissement getRencherissement() {
        return rencherissement;
    }

    public void setRencherissement(Rencherissement rencherissement) {
        this.rencherissement = rencherissement;
    }

    public String getMembre_id() {
        return membre_id;
    }

    public void setMembre_id(String membre_id) {
        this.membre_id = membre_id;
    }
}
