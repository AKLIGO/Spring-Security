package com.continuer.Signup_Login.Services;

import org.springframework.stereotype.Service;

import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.FichierDeValidation.Validation;


import java.time.Instant;
import com.continuer.Signup_Login.Repository.*;
import java.time.temporal.ChronoUnit;
import java.util.Random;



@Service
public class ValidationsService {
    private final NotificationServices notificationServices;
    private final ValidationRepository validationRepository;

    
    public ValidationsService(ValidationRepository validationRepository,
                              
                              NotificationServices notificationServices) {
        this.validationRepository = validationRepository;
       
        this.notificationServices = notificationServices;
    }
    public void createValidation(Users user){
            Validation validation = new Validation();
            validation.setUser(user);
            Instant creation=Instant.now();

            validation.setCreation(creation);

            Instant expiration = creation.plus(10, ChronoUnit.MINUTES);

            validation.setExpiration(expiration);

            
            Random random=new Random();
            int randomInteger=random.nextInt(100000);
            String code=String.format("%06d", randomInteger);
            validation.setCode(code);
            // validation.setCreation(creation);
            // validation.setExpiration(expiration);
            validationRepository.save(validation);
            this.notificationServices.envoyer(validation);
            // Envoyer l'email de validation
            // sendValidationEmail(user, code);
    }
    public Validation lireEnFctionDuCode(String code){
        return this.validationRepository.findByCode(code);
    }
    
}
