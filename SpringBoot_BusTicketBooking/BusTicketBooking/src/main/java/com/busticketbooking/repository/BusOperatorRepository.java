package com.busticketbooking.repository;

import com.busticketbooking.enums.OperatorStatus;
import com.busticketbooking.model.BusOperator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusOperatorRepository
        extends JpaRepository<BusOperator, Integer> {

    BusOperator findByEmail(String email);

    List<BusOperator>
    findByStatus(
            OperatorStatus status
    );
}