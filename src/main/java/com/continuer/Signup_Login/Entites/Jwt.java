package com.continuer.Signup_Login.Entites;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor

@Setter
@Getter
@Entity
public class Jwt extends BaseEntity{
    private boolean desactive;
    private boolean expire;
    private String valeur;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private Users user;
    public Jwt() {
    }
}
