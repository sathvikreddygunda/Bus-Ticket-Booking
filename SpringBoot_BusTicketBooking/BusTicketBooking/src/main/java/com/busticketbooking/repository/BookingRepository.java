package com.busticketbooking.repository;

import com.busticketbooking.enums.BookingStatus;
import com.busticketbooking.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
Repository Layer

Handles Booking DB operations
*/

public interface BookingRepository
        extends JpaRepository<Booking,Integer> {
    /*
    Derived Query Traversal

    Booking -> Customer -> CustomerId
    */
    List<Booking>
    findByCustomerCustomerId(
            int customerId);

    List<Booking> findByBookingStatus(
            BookingStatus bookingStatus);
    long countByBookingStatus(
            BookingStatus bookingStatus);
    Page<Booking> findByCustomerCustomerId(
            int customerId,
            Pageable pageable);

    @Query("""
       SELECT b
       FROM Booking b
       WHERE b.route.bus.busOperator.email = :email
       """)
    List<Booking> getBookingsByOperatorEmail(
            @Param("email")
            String email);
}