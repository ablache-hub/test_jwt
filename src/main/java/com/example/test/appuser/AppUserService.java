package com.example.test.appuser;


import com.example.test.security.PasswordConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


//Trouver un utilisateur
@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("L'utilisateur dont l'email est: "+email+" est introuvable")));
    }

    public String signUpUser(AppUser appUser){
        boolean exist = appUserRepository.
                findByEmail(appUser.getEmail()).
                isPresent();

        if(exist) {
            throw new IllegalStateException("Cet email est déjà utilisé");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        return "it works";
    }
}


