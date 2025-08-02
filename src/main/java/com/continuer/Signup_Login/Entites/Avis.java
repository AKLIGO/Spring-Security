package com.continuer.Signup_Login.Entites;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Avis extends BaseEntity{

    private String message;
    private String status;
    @ManyToOne
    Users user;
    
}
