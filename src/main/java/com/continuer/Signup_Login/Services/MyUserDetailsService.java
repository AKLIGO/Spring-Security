package com.continuer.Signup_Login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.continuer.Signup_Login.Repository.UsersRepository;

import lombok.AllArgsConstructor;
// import lombok.NoArgsConstructor;

import com.continuer.Signup_Login.Entites.Users;

// @NoArgsConstructor
@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

        private final UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getAuthorities() // ou : Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
