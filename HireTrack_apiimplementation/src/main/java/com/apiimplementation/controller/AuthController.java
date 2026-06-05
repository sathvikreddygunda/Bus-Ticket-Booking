package com.apiimplementation.controller;

import com.apiimplementation.dto.RegisterRequestDto;
import com.apiimplementation.service.UserService;
import com.apiimplementation.utility.JwtUtility;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final JwtUtility jwtUtility;
    private final UserService userService;


    // Generate JWT token after successful Basic Authentication
    @GetMapping("/login")
    public String login(Principal principal){

        String username = principal.getName();

        String token = jwtUtility.generateToken(username);

        return token;
    }
    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequestDto dto) {

        userService.register(dto);

        return "User Registered Successfully";
    }



}