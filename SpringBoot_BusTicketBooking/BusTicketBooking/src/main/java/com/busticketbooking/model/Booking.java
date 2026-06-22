package com.busticketbooking.model;

import com.busticketbooking.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
/*
Booking Entity

Represents a ticket booked by a User
for a particular Route and Seat.
*/

@Entity
@Getter
@Setter
@Table(name = "booking")

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @OneToMany(mappedBy = "booking",
            cascade = CascadeType.ALL)
    private List<Passenger> passengers;

    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @CreationTimestamp
    private Instant bookingDate;

    /*
    Many Bookings can belong to one Customer
    */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /*
    Many Bookings can belong to one Route
    */
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

}