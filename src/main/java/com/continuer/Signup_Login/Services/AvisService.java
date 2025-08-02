package com.continuer.Signup_Login.Services;

import com.continuer.Signup_Login.Entites.Avis;
import com.continuer.Signup_Login.Repository.AvisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
@AllArgsConstructor
@Service
public class AvisService {
    private AvisRepository avisRepository;
    public Avis addAvis(Avis avis){
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return avisRepository.save(avis);
    }
    public Avis getAvis(Long id){
        return avisRepository.findById(id).orElse(null);
    }
}
