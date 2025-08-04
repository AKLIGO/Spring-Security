package com.continuer.Signup_Login.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.continuer.Signup_Login.Entites.Jwt;
import com.continuer.Signup_Login.Entites.Users;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Jwt findByUser(Users user);

    
}
