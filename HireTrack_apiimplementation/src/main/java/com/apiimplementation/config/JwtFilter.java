package com.apiimplementation.config;

import com.apiimplementation.service.UserService;
import com.apiimplementation.utility.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // JWT utility for validation
    private final JwtUtility jwtUtility;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Read Authorization header
        final String authorizationHeader =
                request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        try {

            // Check if Bearer token is present
            if (authorizationHeader != null
                    && authorizationHeader.startsWith("Bearer ")) {

                // Extract token
                jwt = authorizationHeader.substring(7);

                // Extract username from token
                username = jwtUtility.extractUsername(jwt);
            }

            // Authenticate only if user is not already authenticated
            if (username != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                // Load user details
                UserDetails userDetails =
                        userService
                                .loadUserByUsername(username);

                // Validate token
                boolean status =
                        jwtUtility.validateToken(
                                jwt,
                                userDetails.getUsername());

                if (status) {

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            filterChain.doFilter(request, response);
        }
    }
}