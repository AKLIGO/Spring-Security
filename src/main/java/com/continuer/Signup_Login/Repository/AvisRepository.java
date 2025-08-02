package com.continuer.Signup_Login.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.continuer.Signup_Login.Entites.Avis;
@Repository
public interface AvisRepository extends JpaRepository<Avis, Long>{
    
}
