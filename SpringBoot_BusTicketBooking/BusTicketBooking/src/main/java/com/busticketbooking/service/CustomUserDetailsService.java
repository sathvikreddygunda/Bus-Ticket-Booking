package com.busticketbooking.service;

import com.busticketbooking.model.BusOperator;
import com.busticketbooking.model.User;
import com.busticketbooking.repository.BusOperatorRepository;
import com.busticketbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    private final BusOperatorRepository
            busOperatorRepository;
    private static final Logger logger =
            LoggerFactory.getLogger(
                    CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(
            String email)
            throws UsernameNotFoundException {

        logger.info(
                "Authenticating user with email: {}",
                email);

        User user =
                userRepository.findByEmail(email);

        if(user != null){

            logger.info(
                    "User login successful: {}",
                    user.getEmail());

            return org.springframework.security
                    .core.userdetails.User
                    .builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        }

        BusOperator operator =
                busOperatorRepository
                        .findByEmail(email);

        if(operator != null){

            logger.info(
                    "Operator login successful: {}",
                    operator.getEmail());

            return org.springframework.security
                    .core.userdetails.User
                    .builder()
                    .username(operator.getEmail())
                    .password(operator.getPassword())
                    .roles("OPERATOR")
                    .build();
        }

        logger.error(
                "Invalid login attempt for email: {}",
                email);

        throw new UsernameNotFoundException(
                "Invalid Email");
    }
}