package com.continuer.Signup_Login.Services;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.continuer.Signup_Login.Enumeraterur.TypeDeRoles;
import com.continuer.Signup_Login.FichierDeValidation.Validation;

import java.util.List;
import java.util.Map;
import java.time.Instant;


import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.Entites.Role;

import com.continuer.Signup_Login.Repository.UsersRepository;
import com.continuer.Signup_Login.Repository.RoleRepository;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class UsersService {

    @Autowired
    private  UsersRepository usersRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private ValidationsService validationsService;

    // public UsersService(UsersRepository usersRepository,
    //                     RoleRepository roleRepository,
    //                     PasswordEncoder passwordEncoder) {
    //     this.usersRepository = usersRepository;
    //     this.roleRepository = roleRepository;
    //     this.passwordEncoder = passwordEncoder;
    // }

    public Users createUser(Users users) {
          if (usersRepository.existsByUsername(users.getUsername())) {
        throw new RuntimeException("Ce nom d'utilisateur existe déjà !");
    }
        // Encodage du mot de passe
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        // Définir la date de création
        users.setCreatedAt(LocalDateTime.now());

        
        

        // Affecter automatiquement le rôle "UTILISATEUR"
        Role defaultRole = roleRepository.findByLibelle(TypeDeRoles.UTILISATEUR) // Correction de la faute de frappe ici
                             .orElseThrow(() -> new RuntimeException("Le rôle UTILISATEUR n'existe pas !"));
        users.setRole(defaultRole);

        Users utilisateur = this.usersRepository.save(users);
        this.validationsService.createValidation(utilisateur);
        return utilisateur;
    }

    public void activation(Map<String,String> activation) {
        Validation validation=this.validationsService.lireEnFctionDuCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Votre code a expiré");
        }
       Users userActiver= this.usersRepository.findById(validation.getUser().getId()).orElseThrow(()-> new RuntimeException("utilisateur inconnu"));
       userActiver.setActif(true);
       this.usersRepository.save(userActiver);

    }

    public List<Users> listUsers() {
        return this.usersRepository.findAll();

    }
}   
