package com.continuer.Signup_Login.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.continuer.Signup_Login.FichierDeValidation.Validation;
import com.continuer.Signup_Login.Entites.Users;
import java.util.Optional;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long>{
    Optional<Validation> findByCode(String code);
    Validation findByUser(Users user);
}