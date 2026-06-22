package com.busticketbooking.model;

import com.busticketbooking.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "seat")

public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @CreationTimestamp
    private Instant createdAt;

    /*
    Many Seats can belong to one Bus
    */
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;
}