package com.busticketbooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record BusOperatorDto(

        @NotBlank(message = "Operator Name is mandatory")
        String operatorName,

        @Email(message = "Invalid Email")
        @NotBlank(message = "Email is mandatory")
        String email,

        @NotBlank(message = "Password is mandatory")
        String password,

        @NotBlank(message = "Company Name is mandatory")
        String companyName,

        @NotBlank(message = "Phone is mandatory")
        String phone
) {
}