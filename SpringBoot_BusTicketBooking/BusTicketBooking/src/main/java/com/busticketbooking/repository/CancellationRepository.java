package com.busticketbooking.repository;

import com.busticketbooking.model.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancellationRepository
        extends JpaRepository<Cancellation,Integer> {
}