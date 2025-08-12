package com.continuer.Signup_Login.Services;

import com.continuer.Signup_Login.Entites.Avis;
import com.continuer.Signup_Login.Repository.AvisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.Repository.UsersRepository;
@AllArgsConstructor
@Service
public class AvisService {
    private AvisRepository avisRepository;
    private UsersRepository userRepository;
 public Avis addAvis(Avis avis){
    // Étape 1 : Récupérer le nom d'utilisateur de l'utilisateur connecté
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username;

    if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
        username = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
    } else {
        username = principal.toString(); // moins sûr, à éviter
    }

    // Étape 2 : Charger l'utilisateur depuis la base
    Users user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + username));

    // Étape 3 : Associer le user à l’avis
    avis.setUser(user);

    // Étape 4 : Sauvegarder l'avis
    return avisRepository.save(avis);
}

    public Avis getAvis(Long id){
        return avisRepository.findById(id).orElse(null);
    }

    public List<Avis> listAvis() {
        
        return avisRepository.findAll();
    }
    public void deleteAvis(Long id) {
        avisRepository.deleteById(id);
    }
}
