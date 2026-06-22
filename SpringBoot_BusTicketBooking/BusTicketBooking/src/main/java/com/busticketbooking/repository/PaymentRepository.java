package com.busticketbooking.repository;

import com.busticketbooking.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository
        extends JpaRepository<Payment,Integer> {
}