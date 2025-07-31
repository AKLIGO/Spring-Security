package com.continuer.Signup_Login.configuration;

import com.continuer.Signup_Login.Entites.Role;
import com.continuer.Signup_Login.Enumeraterur.TypeDeRoles;
import com.continuer.Signup_Login.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Créer le rôle UTILISATEUR s'il n'existe pas
        if (roleRepository.findByLibelle(TypeDeRoles.UTILISATEUR).isEmpty()) {
            Role userRole = new Role();
            userRole.setLibelle(TypeDeRoles.UTILISATEUR);
            roleRepository.save(userRole);
            System.out.println("Rôle UTILISATEUR créé avec succès");
        }

        // Créer le rôle ADMINISTRATEUR s'il n'existe pas
        if (roleRepository.findByLibelle(TypeDeRoles.ADMINISTRATEUR).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setLibelle(TypeDeRoles.ADMINISTRATEUR);
            roleRepository.save(adminRole);
            System.out.println("Rôle ADMINISTRATEUR créé avec succès");
        }
    }
}