package com.busticketbooking.controller;

import com.busticketbooking.dto.*;
import com.busticketbooking.enums.Role;
import com.busticketbooking.model.BusOperator;
import com.busticketbooking.model.User;
import com.busticketbooking.service.BusOperatorService;
import com.busticketbooking.service.UserService;
import com.busticketbooking.utility.JwtUtility;
import lombok.AllArgsConstructor;
import com.busticketbooking.enums.OperatorStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.busticketbooking.dto.RegisterDto;


/*
Authentication Controller

Responsibilities:

1. Login User
2. Generate JWT Token
*/

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;
    private final UserService userService;
    private final BusOperatorService busOperatorService;

    /* Register API
    User Details

    Output: User Registered Successfully
    */
    @PostMapping("/register")
    public String register(
            @RequestBody RegisterDto dto){

        userService.register(dto);

        return "User Registered Successfully";
    }

    /*
    Login API
    Email + Password

    Output:
    JWT Token
    */
    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto dto){

        System.out.println("LOGIN API HIT");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password())
        );

        String token = jwtUtility.generateToken(
                        dto.email());
        User user = userService.getByEmail(dto.email());

        return new LoginResponseDto(
                dto.email(),
                user.getRole().toString(),
                token);
    }

    /*
    Operator Login

    Email + Password
    */
    @PostMapping("/operator/login")
    public LoginResponseDto operatorLogin(
            @RequestBody LoginRequestDto dto) {
        System.out.println("OPERATOR LOGIN HIT");

        BusOperator operator =
                busOperatorService
                        .getByEmail(
                                dto.email());

        if(operator == null){

            throw new RuntimeException(
                    "Operator Not Found");
        }

        if(operator.getStatus()
                == OperatorStatus.PENDING){

            throw new RuntimeException(
                    "Awaiting Admin Approval");
        }

        if(operator.getStatus()
                == OperatorStatus.REJECTED){

            throw new RuntimeException(
                    "Registration Rejected");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password())
        );

        String token =
                jwtUtility.generateToken(
                        dto.email());

        User user =
                userService.getByEmail(
                        dto.email());

        return new LoginResponseDto(
                dto.email(),
                user.getRole().toString(),
                token
        );
    }

    // forgot password

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestBody ForgotPasswordDto dto){

        userService.resetPassword(
                dto.email(),
                dto.newPassword());

        return "Password Reset Successfully";
    }
    
}