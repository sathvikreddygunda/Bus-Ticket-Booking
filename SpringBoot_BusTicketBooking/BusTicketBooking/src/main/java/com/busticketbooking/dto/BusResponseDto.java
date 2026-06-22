package com.busticketbooking.dto;

import com.busticketbooking.enums.BusType;

public record BusResponseDto(

        int busId,
        String busName,
        String busNumber,
        BusType busType,
        int totalSeats,
        double fareAmount,
        BusOperatorResponseDto operator

) {
}