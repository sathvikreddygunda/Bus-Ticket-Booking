package com.busticketbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RouteDto(

        @NotBlank(message = "Source is mandatory")
        String source,

        @NotBlank(message = "Destination is mandatory")
        String destination,

        String pickupPoint,

        String dropPoint,

        @NotNull(message = "Departure Time is mandatory")
        LocalDateTime departureTime,

        @NotNull(message = "Arrival Time is mandatory")
        LocalDateTime arrivalTime,

        @NotNull(message = "Journey Date is mandatory")
        LocalDate journeyDate

) {
}