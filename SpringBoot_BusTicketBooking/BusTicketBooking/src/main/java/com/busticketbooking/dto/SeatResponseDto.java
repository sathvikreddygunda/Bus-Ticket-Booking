package com.busticketbooking.dto;

public record SeatResponseDto(

        int seatId,

        String seatNumber,

        String seatStatus,

        String busName,

        String busNumber

) {
}