package com.example.enchere.entity;

import javax.persistence.*;

@Entity
public class DureeEnchere {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_duree_enchere", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    private int min, max;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
