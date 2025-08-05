package com.continuer.Signup_Login.Controllers;

import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.Services.UsersService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMethod;
import com.continuer.Signup_Login.dtos.AuthentificationDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.continuer.Signup_Login.configuration.JwtService;
// import org.springframework.http.HttpStatus;


import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
public class UsersControllers {

    @Autowired
    private UsersService usersService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users users) {
        System.out.println("Requête POST /register reçue avec utilisateur : " + users.getUsername());
        Users createdUser = usersService.createUser(users);
        System.out.println("Utilisateur créé avec ID : " + createdUser.getId());
        return createdUser;
    }
    @PostMapping("/activation")
    public void activateUser(@RequestBody Map<String,String> activation) {
        this.usersService.activation(activation);
    }
   @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody AuthentificationDto authenticationDto) {
        try {
            Authentication authenticates = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.username(), authenticationDto.password())
            );

        if(authenticates.isAuthenticated()){
            return this.jwtService.generate(authenticationDto.username());

        }
        return Map.of("message", "Connexion réussie");
    } catch (AuthenticationException e) {
        log.warn("Échec de l'authentification : " + e.getMessage());
        return Map.of("message", "Échec de la connexion");
    }

}

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String, String> logoutUser() {
        this.jwtService.logout();
        return Map.of("message", "Déconnexion réussie");
    }
}