package com.continuer.Signup_Login.Repository;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.continuer.Signup_Login.Entites.Jwt;
import com.continuer.Signup_Login.Entites.Users;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Jwt findByUser(Users user);
   Optional<Jwt> findByValeur(String valeur);
   Optional<Jwt> findByValeurAndDesactiveAndExpire(String valeur, boolean desactive, boolean expire);

   @Query("select j from Jwt j where j.user.username=:username and j.desactive=:desactive and j.expire=:expire")
   Optional<Jwt> findUserValidToken(String username, boolean desactive, boolean expire);

   @Query("select j from Jwt j where j.user.username=:username and j.desactive=:desactive and j.expire=:expire")
   Optional<Jwt> findUser(String username, boolean desactive, boolean expire);
   @Query("select j from Jwt j where j.user.username=:username")
   Stream<Jwt> findUserByUsername(String username);

   @Query("select j from Jwt j where j.refreshToken.valeur=:valeur")
   Optional<Jwt> findByRefreshToken(String valeur);






    
}
