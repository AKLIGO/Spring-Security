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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import com.continuer.Signup_Login.Entites.Users;
import com.continuer.Signup_Login.Repository.UsersRepository;
import com.continuer.Signup_Login.Services.MyUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

    //Authenfication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService(UsersRepository usersRepository) {
        return new MyUserDetailsService(usersRepository);
        };

        @Bean
public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
}
    }

