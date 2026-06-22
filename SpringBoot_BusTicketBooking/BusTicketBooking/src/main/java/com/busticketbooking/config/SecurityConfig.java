package com.busticketbooking.config;


import com.busticketbooking.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor

public class SecurityConfig {

    private final JwtFilter jwtFilter;
    /*
    Password Encoder:
    DB contains plain text passwords.

    BCryptPasswordEncoder
    */
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    /*
    Authentication Manager
    */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    /*
    Security Rules
    */
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http)
            throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> {});

        http.authorizeHttpRequests(auth -> auth

                /*
                Public APIs
                Anyone can access the app
                */
                .requestMatchers(
                        "/api/auth/**",
                        "/api/operator/register",
                        // Bus Search APIs
                        "/api/bus/all",
                        "/api/bus/all-page",
                        "/api/bus/get-one/**",
                        "/api/seat/by-bus/**",
                        "/api/bus/type",
                        // Route Search APIs
                        "/api/route/search",
                        "/api/route/all",
                        "/api/route/get-one/**",
                        "/api/route/by-source",
                        "/api/route/by-destination",
                        "/api/route/sources",
                        "/api/route/destinations",
                        "/api/route/pickup-points",
                        "/api/route/all/v2",
                        "/api/route/drop-points"
                )
                .permitAll()
                .requestMatchers(
                        "/api/seat/by-bus/**",
                        "/api/seat/available/**"
                        ).permitAll()
                /*
                ADMIN ONLY APIs
                */
                .requestMatchers(
                        "/api/admin/**",
                        "/api/operator/**",
                        "/api/customer/all",
                        "/api/user/all-page",
                        "/api/booking/all-page",
                        "/api/booking/status",
                        "/api/booking/by-customer/**",
                        "/api/cancellation/**",
                        "/api/operator/all",
                        "/api/operator/pending",
                        "/api/operator/approve/**",
                        "/api/operator/reject/**"
                )
                .hasRole("ADMIN")

                /*
                OPERATOR APIs
                */
                .requestMatchers(
                        "/api/bus/add",
                        "/api/bus/my-buses",
                        "/api/route/add/**",
                        "/api/route/my-routes",
                        "/api/seat/**",
                        "/api/booking/operator-bookings"
                )
                .hasAnyRole("ADMIN","OPERATOR")

                /*
                USER APIs
                asks for login
                */
                .requestMatchers(
                        "/api/booking/add/**",
                        "/api/booking/cancel/**",
                        "/api/booking/delete/**",
                        "/api/booking/my-bookings",
                        "/api/payment/**",
                        "/api/user/me",
                        "/api/customer/me"
                )
                .hasAnyRole("USER","ADMIN", "CUSTOMER")

                /*
                Remaining APIs
                Authentication Required
                */
                .anyRequest()
                .authenticated()
        );

        /*
        JWT Filter
        */
        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}