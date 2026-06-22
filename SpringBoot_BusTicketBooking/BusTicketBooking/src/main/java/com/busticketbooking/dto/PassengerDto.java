package com.busticketbooking.dto;

public record PassengerDto(
        String passengerName,

        int age,

        String gender,

        int seatId
) {
}
