package com.continuer.Signup_Login.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final com.continuer.Signup_Login.Services.MyUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, 
                                  com.continuer.Signup_Login.Services.MyUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Si pas d'en-tête Authorization ou s'il ne commence pas par "Bearer ", on passe au filtre suivant
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraction du token (en enlevant "Bearer ")
        jwt = authHeader.substring(7);
        
        try {
            // Extraction du nom d'utilisateur à partir du token
            username = jwtService.extractUsername(jwt);
            
            // Si le nom d'utilisateur existe et qu'il n'y a pas déjà d'authentification
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Chargement des détails de l'utilisateur
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                
                // Validation du token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Création d'un token d'authentification Spring Security
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    // Ajout des détails de la requête
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Mise à jour du contexte de sécurité
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // En cas d'erreur lors de la validation du token, on continue sans authentification
            logger.error("Impossible de valider le token JWT", e);
        }
        
        // Passage au filtre suivant
        filterChain.doFilter(request, response);
    }
}