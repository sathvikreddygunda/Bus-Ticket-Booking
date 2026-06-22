package com.busticketbooking.dto;

import com.busticketbooking.enums.BusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BusDto(

        @NotBlank(message = "Bus Name is mandatory")
        String busName,

        @NotBlank(message = "Bus Number is mandatory")
        String busNumber,

        @NotNull(message = "Bus Type is mandatory")
        BusType busType,

        @Positive(message = "Total seats must be positive")
        int totalSeats,

        @Positive(message = "Fare amount must be positive")
        double fareAmount
) {
}