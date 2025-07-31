package com.continuer.Signup_Login.configuration;

// import com.continuer.Signup_Login.Entites.Users;
// import com.continuer.Signup_Login.Repository.UsersRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class ConfigurationSecurityApplication {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean UserDetailsService personnalisé
    // @Bean
    // public UserDetailsService userDetailsService(UsersRepository usersRepository) {
    //     return username -> {
    //         Users user = usersRepository.findByUsername(username);
    //         if (user == null) {
    //             throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
    //         }
    //         return user;
    //     };
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", "/auth/**", "/login", "/register","/activation").permitAll()
                .anyRequest().authenticated()
        )
        .formLogin(form -> form.permitAll())
        .logout(logout -> logout.permitAll())
        
        .httpBasic(withDefaults());

        return http.build();
    }
}
