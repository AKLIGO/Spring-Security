package com.continuer.Signup_Login.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.Services.UsersService;



@AllArgsConstructor
@RequestMapping("/utilisateur")

@RestController
public class UtilisateurControllers {
    private final UsersService usersService;

    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATEUR')")

    @GetMapping("/list_Users")
    public List<Users> getListUsers() {
        return usersService.listUsers();
    }
}
