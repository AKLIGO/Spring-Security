package com.continuer.Signup_Login.Entites;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class RefreshToken extends BaseEntity{

    private String refreshToken;
    // private Date creation;
    private boolean expire;
    private String valeur;
    private Instant creation;

    private Instant refreshTokenDateExpiration;
    
}
