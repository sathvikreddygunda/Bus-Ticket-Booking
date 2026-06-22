package com.busticketbooking.dto;

public record BusOperatorResponseDto(
        int operatorId,
        String operatorName,
        String email,
        String companyName,
        String phone

) {
}