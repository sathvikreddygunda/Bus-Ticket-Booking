package com.busticketbooking.repository;

import com.busticketbooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository
        extends JpaRepository<Booking, Integer> {

}
