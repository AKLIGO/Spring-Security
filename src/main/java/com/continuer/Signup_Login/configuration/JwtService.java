package com.continuer.Signup_Login.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.continuer.Signup_Login.Repository.UsersRepository;
import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.Services.MyUserDetailsService;

import lombok.AllArgsConstructor;

import java.security.Key;

@AllArgsConstructor
@Service
public class JwtService {
    private MyUserDetailsService myUserDetailsService;
    private UsersRepository usersRepository;
    
    // Clé secrète pour signer le token (idéalement à mettre dans application.properties)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Durée de validité du token (24 heures)
    private static final long EXPIRATION_TIME = 86400000; // 24 heures en millisecondes
    
    /**
     * Génère un token JWT pour l'utilisateur spécifié
     * @param username Nom d'utilisateur
     * @return Map contenant le token JWT et sa date d'expiration
     */
    public Map<String, String> generate(String username) {
        Users user = usersRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));;
        
        // Date d'expiration du token
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        
        // Création du token
        String token = Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                
                .expiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
        
        // Retourne le token et sa date d'expiration
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("expiresAt", String.valueOf(expirationDate.getTime()));
        tokenMap.put("username", user.getUsername());
        
        return tokenMap;
    }
    
    /**
     * Extrait le nom d'utilisateur du token JWT
     * @param token Token JWT
     * @return Nom d'utilisateur
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    /**
     * Vérifie si le token est valide
     * @param token Token JWT
     * @param userDetails Détails de l'utilisateur
     * @return true si le token est valide, false sinon
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    /**
     * Vérifie si le token est expiré
     * @param token Token JWT
     * @return true si le token est expiré, false sinon
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * Extrait la date d'expiration du token JWT
     * @param token Token JWT
     * @return Date d'expiration
     */
    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
    
    /**
     * Extrait toutes les revendications du token JWT
     * @param token Token JWT
     * @return Revendications
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
