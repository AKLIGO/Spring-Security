package com.continuer.Signup_Login.FichierDeValidation;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Table;

import java.time.Instant;

import com.continuer.Signup_Login.Entites.BaseEntity;
import com.continuer.Signup_Login.Entites.Users;
import jakarta.persistence.CascadeType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "validation")
public class Validation extends BaseEntity{
private Instant creation;
private Instant expiration;
private String code;
private Instant activation;
@OneToOne(cascade = CascadeType.ALL)
private Users user;
}
