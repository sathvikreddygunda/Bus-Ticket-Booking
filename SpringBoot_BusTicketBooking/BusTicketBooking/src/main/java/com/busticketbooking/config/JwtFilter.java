package com.busticketbooking.config;

import com.busticketbooking.service.CustomUserDetailsService;
import com.busticketbooking.service.UserService;
import com.busticketbooking.utility.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
JWT Filter

Responsibilities:

1. Read JWT from Authorization Header
2. Extract Email from JWT
3. Validate JWT
4. Set Authentication Context */

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtility jwtUtility;

    private final CustomUserDetailsService
            customUserDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        /*
        Read Authorization Header

        Example:

        Authorization:
        Bearer eyJhbGciOiJIUzI1Ni...
        */
        String authHeader =
                request.getHeader("Authorization");
        String token = null;
        String email = null;

        //Check header exists

        if(authHeader != null && authHeader.startsWith("Bearer ")){

            token = authHeader.substring(7);

            //Extract Email from Token

            try {

                email = jwtUtility.extractEmail(token);

            }
            catch (Exception ex){

                filterChain.doFilter(
                        request,
                        response);

                return;
            }
        }

        // User not already authenticated

        if(email != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null){

            customUserDetailsService
                    .loadUserByUsername(email);
            UserDetails userDetails =
                    customUserDetailsService
                            .loadUserByUsername(email);



            // Validate Token

            if(jwtUtility.validateToken(
                    token,
                    userDetails.getUsername())){
                System.out.println(
                        "Authorities = " +
                                userDetails.getAuthorities());

                System.out.println(
                        "Username = " +
                                userDetails.getUsername());
                System.out.println(
                        "Authentication Set Successfully");

                UsernamePasswordAuthenticationToken
                        authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(
                                authenticationToken);
            }
        }

        filterChain.doFilter(
                request,
                response);
    }

}