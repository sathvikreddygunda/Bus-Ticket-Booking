package com.busticketbooking.dto;

/*
Request DTO for Login
*/

public record LoginRequestDto(
        String email,
        String password

) {
}