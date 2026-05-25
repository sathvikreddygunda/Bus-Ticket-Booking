package com.busticketbooking.model;


import com.busticketbooking.enums.BookingStatus;
import com.busticketbooking.enums.BusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String passengerName;

    private String source;

    private String destination;

    private double fareAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    private BusType busType;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

}
