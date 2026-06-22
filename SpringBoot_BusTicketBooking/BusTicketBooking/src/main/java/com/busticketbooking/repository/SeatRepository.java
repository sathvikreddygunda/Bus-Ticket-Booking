package com.busticketbooking.repository;

import com.busticketbooking.enums.SeatStatus;
import com.busticketbooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface SeatRepository
        extends JpaRepository<Seat, Integer> {

    // Seat → Bus → busId traversal

    List<Seat> findByBusBusId(int busId);

    List<Seat> findBySeatStatus(SeatStatus seatStatus);

    List<Seat> findByBusBusIdAndSeatStatus(
            int busId,
            SeatStatus seatStatus);

    // seat availability jpql

    @Query("""
        select s
        from Seat s
        where s.bus.busId = ?1
        and s.seatStatus = 'AVAILABLE'
        """)
    List<Seat> getAvailableSeatsJPQL(
            int busId);
}