package com.example.enchere.entity;

import com.example.enchere.value_object.Duree;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Enchere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_enchere", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne
    @NotNull
    private Categorie categorie;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "membre_id")
    private Membre membre;

    @Column
    @NotEmpty
    private String description;

    @CreationTimestamp
    private LocalDateTime dateDebut = LocalDateTime.now();

    @Column
    private LocalDateTime dateFin;

    @Transient
    private boolean statut;

    @Column
    @PositiveOrZero
    @NotNull
    private Double prixMinimum = 0d;

    @ElementCollection
    private List<String> photos;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Duree duree;

    public void setDuree(Duree duree) {
        this.duree = duree;
        this.dateFin = calculateDateFin();
    }

    public Duree getDuree() {
        return duree;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    private LocalDateTime calculateDateFin(){
        return dateDebut.plusSeconds(duree.toSeconde());
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public double getPrixMinimum() {
        return prixMinimum;
    }

    public void setPrixMinimum(Double prixMinimum) {
        this.prixMinimum = prixMinimum;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Membre getMembre() {
        return membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }
}
