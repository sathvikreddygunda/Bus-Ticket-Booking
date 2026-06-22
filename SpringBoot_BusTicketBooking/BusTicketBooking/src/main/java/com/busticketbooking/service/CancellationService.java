package com.busticketbooking.service;

import com.busticketbooking.enums.BookingStatus;
import com.busticketbooking.enums.RefundStatus;
import com.busticketbooking.enums.SeatStatus;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.exception.UnauthorizedActionException;
import com.busticketbooking.model.Booking;
import com.busticketbooking.model.Cancellation;
import com.busticketbooking.model.Passenger;
import com.busticketbooking.repository.CancellationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
Service Layer

Contains Cancellation Business Logic
*/

@Service
@AllArgsConstructor

public class CancellationService {

    private final CancellationRepository cancellationRepository;

    private final BookingService bookingService;

    private final SeatService seatService;

    /*
    Cancel Booking and Generate Refund
    */
    public void addCancellation(
            int bookingId,
            String email){

        Booking booking =
                bookingService.getById(bookingId);

        if(!booking.getCustomer()
                .getUser()
                .getEmail()
                .equals(email)){

            throw new UnauthorizedActionException(
                    "You cannot cancel another user's booking");
        }

        booking.setBookingStatus(
                BookingStatus.CANCELLED);

        bookingService.cancelBooking(
                bookingId,
                email);

        for(Passenger passenger
                : booking.getPassengers()){

            seatService.updateSeatStatus(
                    passenger.getSeat().getSeatId(),
                    SeatStatus.AVAILABLE);
        }

        Cancellation cancellation =
                new Cancellation();

        cancellation.setRefundAmount(
                booking.getTotalAmount());

        cancellation.setRefundStatus(
                RefundStatus.PENDING);

        cancellation.setBooking(
                booking);

        cancellationRepository.save(
                cancellation);
    }

    public List<Cancellation> getAll(){

        return cancellationRepository.findAll();
    }

    public Cancellation getById(
            int cancellationId){

        return cancellationRepository
                .findById(cancellationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Cancellation ID"));
    }
}