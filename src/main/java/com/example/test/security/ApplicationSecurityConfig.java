package com.example.test.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) { //
        this.passwordEncoder = passwordEncoder;
    }

    //Implémente l'authentification simple, par défaut un mdp aléatoire sera crée au lancement du serveur avec
    //l'id par défaut "user"
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                antMatchers("/", "/index").
                permitAll().
                anyRequest().
                authenticated().
                and().
                httpBasic();
    }


    //Création d'un user
    @Override
    @Bean
    protected UserDetailsService userDetailsService() { //ctrl+O pour la liste des méthodes de ApplicationSecurityConfig
        UserDetails userDetailsAlex = User.builder()
                .username("alex")
                .password(passwordEncoder.encode("test"))
                .roles("STUDENT")
                .build();

        UserDetails userDetailsBilly = User.builder()
                .username("billy")
                .password(passwordEncoder.encode("test2"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(
                userDetailsAlex,
                userDetailsBilly
        );

    }
}
