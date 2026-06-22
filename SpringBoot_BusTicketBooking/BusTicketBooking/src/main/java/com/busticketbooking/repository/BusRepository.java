package com.busticketbooking.repository;

import com.busticketbooking.enums.BusType;
import com.busticketbooking.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusRepository
        extends JpaRepository<Bus, Integer> {
    /*
    JPQL Query

    Fetch buses by bus type
    */
    @Query("""
            select b
            from Bus b
            where b.busType = ?1
            """)
    List<Bus> getBusByTypeJPQL(
            BusType busType);

    List<Bus> findByBusType(BusType busType);

    List<Bus> findByBusOperatorOperatorId(int operatorId);

    List<Bus> findByBusOperatorEmail(String email);
}