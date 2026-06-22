package com.busticketbooking.dto;

/*
Response DTO after Login
*/

public record LoginResponseDto(
        String email,
        String role,
        String token

) {
}