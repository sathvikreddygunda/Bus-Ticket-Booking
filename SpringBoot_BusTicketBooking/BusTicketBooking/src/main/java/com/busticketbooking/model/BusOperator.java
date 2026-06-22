package com.busticketbooking.model;

import com.busticketbooking.enums.OperatorStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "bus_operator")

public class BusOperator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int operatorId;

    @Column(nullable = false)
    private String operatorName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String phone;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private OperatorStatus status;
}