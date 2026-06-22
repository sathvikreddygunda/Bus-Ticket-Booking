package com.busticketbooking.dto;

import com.busticketbooking.enums.BusType;

public record BusOperatorBusDto(

        int busId,
        String busName,
        String busNumber,
        BusType busType,
        int totalSeats,
        double fareAmount,
        int operatorId,
        String operatorName,
        String companyName
) {
}