package com.busticketbooking.dto;

import com.busticketbooking.enums.BookingStatus;

import java.time.Instant;
import java.util.List;

public record BookingResponseDto(

        int bookingId,

        double totalAmount,

        BookingStatus bookingStatus,

        Instant bookingDate,

        String customerName,

        String source,

        String destination,

        List<String> passengerNames,

        List<String> seatNumbers,

        String busName
) {
}