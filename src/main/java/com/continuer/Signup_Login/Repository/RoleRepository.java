package com.continuer.Signup_Login.Repository;

import com.continuer.Signup_Login.Entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.continuer.Signup_Login.Enumeraterur.TypeDeRoles;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
   
    Optional<Role> findByLibelle(TypeDeRoles libelle);
}
