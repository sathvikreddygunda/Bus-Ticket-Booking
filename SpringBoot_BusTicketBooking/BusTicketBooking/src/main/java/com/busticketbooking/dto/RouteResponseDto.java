package com.busticketbooking.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RouteResponseDto(

        int routeId,

        int busId,

        String source,

        String destination,

        String pickupPoint,

        String dropPoint,

        LocalDate journeyDate,

        LocalDateTime departureTime,

        LocalDateTime arrivalTime,

        String busName,

        String busNumber,

        String operatorName,

        double fareAmount

) {
}