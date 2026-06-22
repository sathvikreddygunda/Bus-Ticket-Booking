package com.busticketbooking.service;

import com.busticketbooking.dto.BookingDto;
import com.busticketbooking.dto.BookingResponseDto;
import com.busticketbooking.dto.PassengerDto;
import com.busticketbooking.enums.BookingStatus;
import com.busticketbooking.enums.SeatStatus;
import com.busticketbooking.exception.InvalidSeatException;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.exception.SeatAlreadyBookedException;
import com.busticketbooking.exception.UnauthorizedActionException;
import com.busticketbooking.mapper.BookingMapper;
import com.busticketbooking.model.*;
import com.busticketbooking.repository.BookingRepository;
import com.busticketbooking.repository.PassengerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.busticketbooking.dto.OperatorBookingDto;

import java.util.List;

/*
Service Layer

Contains Booking Business Logic
*/

@Service
@AllArgsConstructor

public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerService
            customerService;
    private final RouteService routeService;
    private final PassengerRepository
            passengerRepository;
    private final SeatService seatService;
    private final BookingMapper bookingMapper;

    /*
    Create Booking

    FLOW

    User
      ↓
    Route
      ↓
    Seat
      ↓
    Booking
    */
    @Transactional
    public void addBooking(
            BookingDto dto,
            String email,
            int routeId){

        Customer customer =
                customerService
                        .getByEmail(email);

        Route route =
                routeService.getById(routeId);

        if(dto.passengers() == null
                || dto.passengers().isEmpty()){

            throw new RuntimeException(
                    "At least one passenger required");
        }
        /*
Validate all seats first
*/
        for(PassengerDto passengerDto
                : dto.passengers()){

            Seat seat =
                    seatService.getById(
                            passengerDto.seatId());

            if(seat.getBus().getBusId()
                    != route.getBus().getBusId()){

                throw new InvalidSeatException(
                        "Seat does not belong to selected route");
            }

            if(seat.getSeatStatus()
                    == SeatStatus.BOOKED){

                throw new SeatAlreadyBookedException(
                        "Seat Already Booked");
            }
        }

        /*
        Create ONE Booking
        */
        Booking booking =
                new Booking();

        booking.setTotalAmount(
                route.getBus().getFareAmount()
                        * dto.passengers().size());

        booking.setBookingStatus(
                BookingStatus.BOOKED);

        booking.setCustomer(customer);

        booking.setRoute(route);

        bookingRepository.save(
                booking);

        /*
        Create Passengers
        */
        for(PassengerDto passengerDto
                : dto.passengers()){

            Seat seat =
                    seatService.getById(
                            passengerDto.seatId());

            Passenger passenger =
                    new Passenger();

            passenger.setPassengerName(
                    passengerDto.passengerName());

            passenger.setAge(
                    passengerDto.age());

            passenger.setGender(
                    passengerDto.gender());

            passenger.setBooking(
                    booking);

            passenger.setSeat(
                    seat);

            seat.setSeatStatus(
                    SeatStatus.BOOKED);

            passengerRepository.save(
                    passenger);
        }
    }

    /*
    Fetch All Bookings with Pagination

    Example:
    page=0,size=5

    Returns bookings in chunks
    instead of loading all records
    */
    public Page<BookingResponseDto>
    getAllBookings(
            int page,
            int size){

        Pageable pageable =
                PageRequest.of(page, size);

        return bookingRepository
                .findAll(pageable)
                .map(
                        bookingMapper::mapEntityToDto);
    }

    public Booking getById(
            int bookingId){

        return bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Booking ID"));
    }

    /*
    Fetch bookings by Customer
    */
    public List<Booking>
    getByCustomer(int customerId){

        return bookingRepository
                .findByCustomerCustomerId(
                        customerId);
    }

    /*
    Fetch bookings by Status
    */
    public List<Booking>
    getByStatus(
            BookingStatus bookingStatus){

        return bookingRepository
                .findByBookingStatus(
                        bookingStatus);
    }

    /*
    Cancel Booking

    Booking Status
    BOOKED -> CANCELLED

    Seat Status
    BOOKED -> AVAILABLE
    */
    /*
        Cancel Booking

        Validate ownership first
        */
            public void cancelBooking(
            int bookingId,
            String email){

        Booking booking =
                getById(bookingId);

        /*
        Booking Owner Check
        */
                if(!booking.getCustomer()
                        .getUser()
                        .getEmail()
                        .equals(email)){

            throw new UnauthorizedActionException(
                    "You cannot cancel another user's booking");
        }

        booking.setBookingStatus(
                BookingStatus.CANCELLED);

        for(Passenger passenger
                : booking.getPassengers()){

            Seat seat =
                    passenger.getSeat();

            seat.setSeatStatus(
                    SeatStatus.AVAILABLE);
        }

        bookingRepository.save(
                booking);
    }
    /*
    Delete Booking

    Validate ownership first
    */
    public void deleteById(
            int bookingId,
            String email){

        Booking booking =
                getById(bookingId);

    /*
    Booking Owner Check
    */

        if(!booking.getCustomer()
                .getUser()
                .getEmail()
                .equals(email)){

            throw new UnauthorizedActionException(
                    "You cannot cancel another user's booking");
        }

        bookingRepository.deleteById(
                bookingId);
    }
    public BookingResponseDto
    getBookingDetails(
            int bookingId){

        Booking booking =
                getById(bookingId);

        return bookingMapper
                .mapEntityToDto(booking);
    }
    public long getBookingCount(){

        return bookingRepository.count();
    }
    public long getBookedCount(){

        return bookingRepository
                .countByBookingStatus(
                        BookingStatus.BOOKED);
    }

    public long getCancelledCount(){

        return bookingRepository
                .countByBookingStatus(
                        BookingStatus.CANCELLED);
    }

    public Page<BookingResponseDto>
    getMyBookings(
            String email,
            int page,
            int size){

        Customer customer =
                customerService
                        .getByEmail(email);

        Pageable pageable =
                PageRequest.of(page, size);

        return bookingRepository
                .findByCustomerCustomerId(
                        customer.getCustomerId(),
                        pageable)
                .map(
                        bookingMapper::mapEntityToDto
                );
    }
    public List<OperatorBookingDto>
    getOperatorBookings(
            String email){

        return bookingRepository
                .getBookingsByOperatorEmail(
                        email)
                .stream()
                .map(booking ->

                        new OperatorBookingDto(

                                booking.getBookingId(),

                                booking.getCustomer()
                                        .getCustomerName(),

                                booking.getPassengers()
                                        .stream()
                                        .map(passenger ->
                                                passenger.getPassengerName())
                                        .toList(),

                                booking.getRoute()
                                        .getBus()
                                        .getBusName(),

                                booking.getRoute()
                                        .getSource(),

                                booking.getRoute()
                                        .getDestination(),

                                booking.getPassengers()
                                        .stream()
                                        .map(passenger ->
                                                passenger
                                                        .getSeat()
                                                        .getSeatNumber())
                                        .toList(),

                                booking.getTotalAmount(),

                                booking.getBookingStatus()
                                        .name()

                        )

                )
                .toList();
    }
}

    
