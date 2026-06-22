package com.busticketbooking.dto;

import com.busticketbooking.enums.SeatStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SeatDto(

        @NotBlank(message = "Seat Number is mandatory")
        String seatNumber,

        @NotNull(message = "Seat Status is mandatory")
        SeatStatus seatStatus
) {
}