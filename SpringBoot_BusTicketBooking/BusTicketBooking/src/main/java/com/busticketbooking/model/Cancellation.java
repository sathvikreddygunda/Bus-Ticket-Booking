package com.busticketbooking.model;

import com.busticketbooking.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/*
Cancellation Entity

Represents cancellation of a booking.
*/

@Entity
@Getter
@Setter
@Table(name = "cancellation")

public class Cancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cancellationId;

    private double refundAmount;

    @CreationTimestamp
    private Instant cancellationDate;

    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;

    /*
    One Cancellation belongs to one Booking
    */
    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}