package com.busticketbooking.dto;

import java.util.List;

public record OperatorBookingDto(

        int bookingId,

        String customerName,

        List<String> passengerNames,

        String busName,

        String source,

        String destination,

        List<String> seatNumbers,

        double totalAmount,

        String bookingStatus

) {
}