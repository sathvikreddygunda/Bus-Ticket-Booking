package com.busticketbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "route")

public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeId;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    private String pickupPoint;

    private String dropPoint;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private LocalDate journeyDate;

    @CreationTimestamp
    private Instant createdAt;

    /*
    Many Routes can belong to one Bus
    */

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;
}