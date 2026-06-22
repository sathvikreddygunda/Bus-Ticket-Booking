package com.busticketbooking.model;

import com.busticketbooking.enums.BusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "bus")

public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busId;

    @Column(nullable = false)
    private String busName;

    @Column(nullable = false, unique = true)
    private String busNumber;

    @Enumerated(EnumType.STRING)
    private BusType busType;

    @Column(nullable = false)
    private int totalSeats;

    @Column(nullable = false)
    private double fareAmount;

    /*
     MANY buses can belong to ONE operator
     */

    @ManyToOne
    private BusOperator busOperator;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}