package com.continuer.Signup_Login;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.continuer.Signup_Login.Entites.Role;
import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.Enumeraterur.TypeDeRoles;
import com.continuer.Signup_Login.Repository.UsersRepository;
import com.continuer.Signup_Login.Repository.RoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication
public class SignupLoginApplication implements CommandLineRunner {
    
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SignupLoginApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Initialisation des rôles
        initRolesAndUsers();
    }

    private void initRolesAndUsers() {
        // 1. Initialisation des rôles
        Role proprietaireRole = initRole(TypeDeRoles.PROPRIETAIRE);
        Role adminRole = initRole(TypeDeRoles.ADMINISTRATEUR);
        initRole(TypeDeRoles.UTILISATEUR);

        // 2. Initialisation des utilisateurs
        initUser("dovenais@gmail.com", "proprietaire", proprietaireRole);
        initUser("jerome@gmail.com", "admin", adminRole);
    }

    private Role initRole(TypeDeRoles roleType) {
        return roleRepository.findByLibelle(roleType)
            .orElseGet(() -> {
                Role newRole = Role.builder()
                    .libelle(roleType)
                    .build();
                return roleRepository.save(newRole);
            });
    }

    private void initUser(String email, String password, Role role) {
        usersRepository.findByUsername(email)
            .orElseGet(() -> {
                Users user = Users.builder()
                    .actif(true)
                    .username(email)
                    .password(passwordEncoder.encode(password))
                    .role(role) // Utilise le rôle managé
                    .build();
                return usersRepository.save(user);
            });
    }
}