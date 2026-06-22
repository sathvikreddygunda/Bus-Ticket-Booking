package com.busticketbooking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
    public class Customer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int customerId;

        private String customerName;

        private String phone;

        private String address;

        @OneToOne
        @JoinColumn(name = "user_id")
        private User user;
    }

