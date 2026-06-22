package com.busticketbooking.mapper;

import com.busticketbooking.dto.BookingResponseDto;
import com.busticketbooking.model.Booking;
import com.busticketbooking.model.Passenger;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class BookingMapper {

    public BookingResponseDto
    mapEntityToDto(
            Booking booking){

        List<String> passengerNames =
                booking.getPassengers()
                        .stream()
                        .map(Passenger::getPassengerName)
                        .toList();

        List<String> seatNumbers =
                booking.getPassengers()
                        .stream()
                        .map(passenger ->
                                passenger.getSeat()
                                        .getSeatNumber())
                        .toList();

        return new BookingResponseDto(

                booking.getBookingId(),

                booking.getTotalAmount(),

                booking.getBookingStatus(),

                booking.getBookingDate(),

                booking.getCustomer()
                        .getCustomerName(),

                booking.getRoute()
                        .getSource(),

                booking.getRoute()
                        .getDestination(),

                passengerNames,

                seatNumbers,

                booking.getRoute()
                        .getBus()
                        .getBusName()
        );
    }
}