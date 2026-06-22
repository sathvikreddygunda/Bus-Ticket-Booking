package com.busticketbooking.dto;

public record CustomerResponseDto(

        int customerId,

        String customerName,

        String email,

        String phone,

        String address

) {
}
