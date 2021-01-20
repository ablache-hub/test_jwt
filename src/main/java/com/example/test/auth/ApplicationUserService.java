//package com.example.test.auth;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class ApplicationUserService implements UserDetailsService {
//
//    private final ApplicationUserDAO applicationUserDAO;
//
//    public ApplicationUserService(ApplicationUserDAO applicationUserDAO) {
//        this.applicationUserDAO = applicationUserDAO;
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        return applicationUserDAO.selectApplicationUserByUserName(userName)
//                .orElseThrow(() -> new UsernameNotFoundException(String.format("Nom d'utilisateur %s non trouvé", userName)));
//    }
//}
