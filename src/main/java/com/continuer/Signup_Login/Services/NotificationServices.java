package com.continuer.Signup_Login.Services;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.continuer.Signup_Login.FichierDeValidation.Validation;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class NotificationServices {
    JavaMailSender javaMailSender;
    public void envoyer(Validation validation){

        // Envoyer l'email de validation
        
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("koffitsejeromea@gmail.com");
        simpleMailMessage.setTo(validation.getUser().getUsername());
        simpleMailMessage.setSubject("votre code d'activation");
       String text= String.format("votre code d'activation est: %s", 
        validation.getCode());
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);

    }
}
