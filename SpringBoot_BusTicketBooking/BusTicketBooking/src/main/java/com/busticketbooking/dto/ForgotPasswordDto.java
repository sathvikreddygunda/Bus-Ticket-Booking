package com.busticketbooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordDto(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String newPassword
) {
}