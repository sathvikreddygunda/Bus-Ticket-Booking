package com.busticketbooking.model;

import com.busticketbooking.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/*
Payment Entity

Represents payment made for a Booking.
*/

@Entity
@Getter
@Setter
@Table(name = "payment")

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    private String paymentMethod;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @CreationTimestamp
    private Instant paymentDate;

    /*
    One Payment belongs to one Booking
    */
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}