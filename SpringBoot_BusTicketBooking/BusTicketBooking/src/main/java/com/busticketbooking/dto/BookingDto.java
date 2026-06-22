package com.busticketbooking.dto;

import java.util.List;

/*
DTO used while creating Booking
*/

public record BookingDto(
        List<PassengerDto> passengers

) {
}