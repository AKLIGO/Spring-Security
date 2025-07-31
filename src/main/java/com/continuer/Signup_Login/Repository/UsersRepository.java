package com.continuer.Signup_Login.Repository;

import com.continuer.Signup_Login.Entites.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
    Optional<Users> findByUsername(String username);
    boolean existsByUsername(String username);

}
