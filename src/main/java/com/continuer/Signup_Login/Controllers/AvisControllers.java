package com.continuer.Signup_Login.Controllers;

import com.continuer.Signup_Login.Entites.Avis;
import com.continuer.Signup_Login.Services.AvisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController

public class AvisControllers {
    private AvisService avisService;
    @RequestMapping("/addAvis")
    public Avis addAvis(Avis avis){
        return avisService.addAvis(avis);
    }
}
