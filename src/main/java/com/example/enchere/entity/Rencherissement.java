package com.example.enchere.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Rencherissement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "membre_id")
    private Membre membre;

    @ManyToOne
    @JoinColumn(name = "enchere_id")
    private Enchere enchere;

    @Column
    private double prixEnchere;

    @CreationTimestamp
    private LocalDateTime date_enchere;

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    public double getPrixEnchere() {
        return prixEnchere;
    }

    public void setPrixEnchere(double prixEnchere) {
        this.prixEnchere = prixEnchere;
    }

    public LocalDateTime getDate_enchere() {
        return date_enchere;
    }

    public void setDate_enchere(LocalDateTime date_enchere) {
        this.date_enchere = date_enchere;
    }
}
