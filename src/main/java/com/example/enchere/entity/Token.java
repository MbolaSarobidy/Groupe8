package com.example.enchere.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "id_auth")
    @JsonIgnore
    private Authenticable authenticable;

    @Column
    private String value;

    @CreationTimestamp
    private LocalDateTime created_at;

    @Column
    private LocalDateTime expired_at;

    @Transient
    public Long getAuthId(){
        return authenticable.getId();
    }

    public LocalDateTime getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(LocalDateTime expired_at) {
        this.expired_at = expired_at;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Authenticable getAuthenticable() {
        return authenticable;
    }

    public void setAuthenticable(Authenticable authenticable) {
        this.authenticable = authenticable;
    }
}
