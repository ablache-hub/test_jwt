package com.example.test.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {


    // Sera invoqué une fois par requête client
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Recup les uthorisations dans le header du token
        String authorizationHeader = request.getHeader("Authorization");
        String secretKey = "testesttestetststshjjhjhghhghjhhsdfjhsdsdfhjhsdsdhsssfsfghsdfhsffjhsfjhsfjhsjhsfjh";

        // Si null ou ne commence pas pa bearer, on rejete la requête
        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
        }

        try {
            String token = authorizationHeader.replace("Bearer ","");
            Jws<Claims> jwsClaim = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build().parseClaimsJws(token);

            Claims body = jwsClaim.getBody();
            String username = body.getSubject();
            Collection<? extends GrantedAuthority> authorities = (SimpleGrantedAuthority)body.get("authorities");

            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(null);

        }
    }
}