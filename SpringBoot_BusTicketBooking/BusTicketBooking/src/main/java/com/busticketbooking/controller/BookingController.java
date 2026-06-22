package com.busticketbooking.controller;

import com.busticketbooking.dto.BookingDto;
import com.busticketbooking.dto.BookingResponseDto;
import com.busticketbooking.dto.OperatorBookingDto;
import com.busticketbooking.enums.BookingStatus;
import com.busticketbooking.model.Booking;
import com.busticketbooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
Controller Layer

Handles Booking APIs
*/

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingService bookingService;

    /*
    Create Booking
    User + Route + Seat
    */
    @PostMapping("/add/{routeId}")
    public void addBooking(
            @Valid
            @RequestBody BookingDto dto,
            @PathVariable int routeId,
            Principal principal){

        String email = principal.getName();

        bookingService.addBooking(
                dto,
                email,
                routeId);
    }

    // get all bookings
    /*
    Get All Bookings
    with Pagination


    GET -> /api/booking/all-page?page=0&size=5
    */
    @GetMapping("/all-page")
    public Page<BookingResponseDto>
    getAllBookings(
            @RequestParam int page,
            @RequestParam int size){
        return bookingService
                .getAllBookings(
                        page,
                        size);
    }

    /*
    Get bookings by User
    */
    @GetMapping("/by-customer/{customerId}")
    public List<Booking> getByCustomer(
            @PathVariable int customerId){

        return bookingService.getByCustomer(
                customerId);
    }

    /*
    Get bookings by Status
    */
    @GetMapping("/status")
    public List<Booking> getByStatus(
            @RequestParam
            BookingStatus bookingStatus){
        return bookingService.getByStatus(bookingStatus);
    }

    /*
    Cancel Own Booking

    Only booking owner
    can cancel booking
    */
    @PutMapping("/cancel/{bookingId}")
    public void cancelBooking(
            @PathVariable int bookingId,
            Principal principal){

        bookingService.cancelBooking(
                bookingId,
                principal.getName());
    }

    /*
    Delete Own Booking

    Only booking owner
    can delete booking
    */
    @DeleteMapping("/delete/{bookingId}")
    public void deleteById(
            @PathVariable int bookingId,
            Principal principal){

        bookingService.deleteById(
                bookingId,
                principal.getName());
    }

    // Get Logged-In User Bookings

    @GetMapping("/my-bookings")
    public Page<BookingResponseDto>
    myBookings(
            @RequestParam int page,
            @RequestParam int size,
            Principal principal){

        String email =
                principal.getName();

        return bookingService
                .getMyBookings(
                        email,
                        page,
                        size);
    }

    @GetMapping("/get-one/{bookingId}")
    public BookingResponseDto
    getBookingDetails(
            @PathVariable int bookingId){

        return bookingService
                .getBookingDetails(
                        bookingId);
    }
    @GetMapping("/count")
    public long getBookingCount(){

        return bookingService
                .getBookingCount();
    }
    @GetMapping("/count/booked")
    public long getBookedCount(){

        return bookingService
                .getBookedCount();
    }

    @GetMapping("/count/cancelled")
    public long getCancelledCount(){

        return bookingService
                .getCancelledCount();
    }
    @GetMapping("/operator-bookings")
    public List<OperatorBookingDto>
    getOperatorBookings(
            Principal principal){

        return bookingService
                .getOperatorBookings(
                        principal.getName());
    }
}