package com.busticketbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentDto(

        @NotBlank(message = "Payment Method is mandatory")
        String paymentMethod,

        @NotNull(message = "Amount is mandatory")
        double amount

) {
}