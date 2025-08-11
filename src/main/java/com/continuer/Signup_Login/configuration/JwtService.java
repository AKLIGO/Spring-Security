package com.continuer.Signup_Login.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import com.continuer.Signup_Login.Repository.UsersRepository;
import com.continuer.Signup_Login.Entites.Users;
import org.springframework.security.core.Authentication;

import com.continuer.Signup_Login.Entites.Jwt;
import com.continuer.Signup_Login.Entites.RefreshToken;
import com.continuer.Signup_Login.Repository.JwtRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.security.Key;
import java.time.Instant;
import java.util.UUID;



@Transactional
@AllArgsConstructor
@Service
public class JwtService {
    private static final String REFRESH = "REFRESH";
    // private MyUserDetailsService myUserDetailsService;
    private UsersRepository usersRepository;
    private JwtRepository jwtRepository;
    // Clé secrète pour signer le token (idéalement à mettre dans application.properties)
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // Durée de validité du token (24 heures)
    private static final long EXPIRATION_TIME =  1 * 60 * 10000; // 1 minutes
    
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

        // refreh token
        RefreshToken refreshToken = RefreshToken.builder()
                    
                    .expire(false)
                    .valeur(UUID.randomUUID().toString())
                    .creation(Instant.now())
                    .refreshTokenDateExpiration(
                                 Instant.now().plusMillis(30 * 60 * 1000)
                            )
                    .build();
        
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

       final Jwt jwt=Jwt
            .builder()
            .valeur(token)
            .desactive(false)
            .expire(false)
            .user(user)
            .refreshToken(refreshToken)

            .build();
        this.jwtRepository.save(jwt);
        tokenMap.put("refreshToken", refreshToken.getValeur());
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
        Jwt jwtEntity = tokenByValue(token);
        
        // Vérifier que le username Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();du token correspond à celui de l'utilisateur associé au token en base
        return (username.equals(userDetails.getUsername()) && 
                username.equals(jwtEntity.getUser().getUsername()) && 
                !isTokenExpired(token) && 
                !jwtEntity.isDesactive() && 
                !jwtEntity.isExpire());
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
   public Jwt tokenByValue(String valeur){
    return this.jwtRepository.findByValeur(valeur)
        .orElseThrow(() -> new RuntimeException("Token non trouvé"));
};
public void logout() {
   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
        String username = auth.getName(); // Récupère le nom d'utilisateur sans cast
        // Désactiver tous les tokens de l'utilisateur
        disableTokens(username);
        SecurityContextHolder.clearContext();
    }
}

/**
 * Désactive tous les tokens d'un utilisateur spécifique
 * @param username Nom d'utilisateur
 */
public void disableTokens(String username) {
    this.jwtRepository.findUserByUsername(username)
        .forEach(token -> {
            token.setDesactive(true);
            token.setExpire(true);
            this.jwtRepository.save(token);
        });
}
public Map<String,String> refreshToken(Map<String,String> refresTokenRequest){

  final Jwt jwt=this.jwtRepository.findByRefreshToken(refresTokenRequest.get(REFRESH)).orElseThrow(()->new  RuntimeException("token invalid"));
  if (jwt.getRefreshToken().isExpire() || jwt.getRefreshToken().getRefreshTokenDateExpiration().isBefore(Instant.now())) {
    throw new RuntimeException("TOKEN_INVALIDE");
  }
  Map<String,String> tokens=this.generate(jwt.getUser().getUsername());
  this.disableTokens(jwt.getUser().getUsername());
 return tokens;



}

}
