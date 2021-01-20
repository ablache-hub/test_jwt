package com.example.test.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Permet l'utilisation de PreAuthorize dans le controller
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

//    public class MvcConfig implements WebMvcConfigurer {
//
//        @Override
//        public void addViewControllers(ViewControllerRegistry registry) {
//            registry.addViewController("/login").setViewName("login");
//        }
//    }


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
                csrf().disable().
                authorizeRequests().
                antMatchers("/", "/index").permitAll().
                anyRequest().
                authenticated().
                and().
                formLogin().
                loginPage("/login").permitAll().
                defaultSuccessUrl("/default", true);
//                httpBasic();
    }

    //Création d'un user
    @Override
    @Bean
    protected UserDetailsService userDetailsService() { //ctrl+O pour la liste des méthodes de ApplicationSecurityConfig
        UserDetails userDetailsAlex = User.builder()
                .username("alex")
                .password(passwordEncoder.encode("test"))
//                .roles(ApplicationUserRole.STUDENT.name())
                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
                .build();

        UserDetails userDetailsBilly = User.builder()
                .username("billy")
                .password(passwordEncoder.encode("test"))
//                .roles(ApplicationUserRole.ADMIN.name())
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())

                .build();

        UserDetails userDetailsTom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("test"))
//                .roles(ApplicationUserRole.ADMINTEST.name())
                .authorities(ApplicationUserRole.ADMINTEST.getGrantedAuthorities())

                .build();

        return new InMemoryUserDetailsManager(
                userDetailsAlex,
                userDetailsBilly,
                userDetailsTom
        );

    }
}
