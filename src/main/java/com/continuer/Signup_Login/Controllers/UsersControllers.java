package com.continuer.Signup_Login.Controllers;

import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.Services.UsersService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// import org.springframework.http.HttpStatus;


@RestController
public class UsersControllers {

    @Autowired
    private UsersService usersService;

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
}
