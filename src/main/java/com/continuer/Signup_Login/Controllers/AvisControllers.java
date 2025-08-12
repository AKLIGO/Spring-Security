package com.continuer.Signup_Login.Controllers;

import com.continuer.Signup_Login.Entites.Avis;
import com.continuer.Signup_Login.Services.AvisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@AllArgsConstructor
@RestController

public class AvisControllers {
    private AvisService avisService;
    @RequestMapping("/addAvis")
    public Avis addAvis(@RequestBody Avis avis){
        return avisService.addAvis(avis);
    }

    @GetMapping("/list_Avis")
    public List<Avis> getMethodName() {
        return avisService.listAvis();
    }
    

   
}
