package com.example.test.security.config;


import com.example.test.appuser.AppUserService;
import com.example.test.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@EnableWebMvc
@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Permet l'utilisation de PreAuthorize dans le controller
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {


    private AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //Implémente l'authentification simple, par défaut un mdp aléatoire sera crée au lancement du serveur avec
    //l'id par défaut "user"
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
          /*     On importe notre filtre crée précedement, il récupere les données de connection, vérifie leur validité
                 crée et retourne le JWT  */
        addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager())).
                authorizeRequests().
                antMatchers("/", "/index", "/api/v*/registration/**").permitAll().
                anyRequest().
                authenticated();
//                and().
//                formLogin();
//                loginPage("/login").permitAll().
//                defaultSuccessUrl("/default", true);
//                httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    //An AuthenticationProvider implementation that retrieves user details from a UserDetailsService.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

    //Création d'un user
    /*@Override
    @Bean
    protected UserDetailsService userDetailsService() { //ctrl+O pour la liste des méthodes de ApplicationSecurityConfig
        UserDetails userDetailsAlex = User.builder()
                .username("alex")
                .password(passwordEncoder.encode("test"))
//                .roles(AppUserRole.STUDENT.name())
                .authorities(AppUserRole.STUDENT.getGrantedAuthorities())
                .build();

        UserDetails userDetailsBilly = User.builder()
                .username("billy")
                .password(passwordEncoder.encode("test"))
//                .roles(AppUserRole.ADMIN.name())
                .authorities(AppUserRole.ADMIN.getGrantedAuthorities())

                .build();

        UserDetails userDetailsTom = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("test"))
//                .roles(AppUserRole.ADMINTEST.name())
                .authorities(AppUserRole.ADMINTEST.getGrantedAuthorities())

                .build();

        return new InMemoryUserDetailsManager(
                userDetailsAlex,
                userDetailsBilly,
                userDetailsTom
        );

    }*/

  /*      public class MvcConfig implements WebMvcConfigurer {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("login");
        }
    }*/
}
