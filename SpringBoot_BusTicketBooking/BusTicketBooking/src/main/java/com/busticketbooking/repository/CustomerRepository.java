package com.busticketbooking.repository;

import com.busticketbooking.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByUserEmail(
            String email);
}

