package com.busticketbooking.repository;

import com.busticketbooking.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RouteRepository
        extends JpaRepository<Route, Integer> {

        /*
        Repository Layer

        Handles DB operations for Route Entity.
        */

    List<Route> findBySource(String source);

    List<Route> findByDestination(String destination);

    /*
    Route -> Bus -> BusOperator -> OperatorId
    */
    List<Route>
    findByBusBusOperatorOperatorId(
            int operatorId);

    // search route

    @Query("""
        select r
        from Route r
        where r.source = ?1
        and r.destination = ?2
        and r.journeyDate = ?3
        """)
    List<Route> searchRoutesJPQL(
            String source,
            String destination,
            LocalDate journeyDate);

    @Query("""
        SELECT DISTINCT r.source
        FROM Route r
        WHERE LOWER(r.source)
        LIKE LOWER(CONCAT('%', ?1, '%'))
        """)
    List<String> searchSources(
            String keyword);

    @Query("""
        SELECT DISTINCT r.destination
        FROM Route r
        WHERE LOWER(r.destination)
        LIKE LOWER(CONCAT('%', ?1, '%'))
        """)
    List<String> searchDestinations(
            String keyword);

    @Query("""
        SELECT DISTINCT r.pickupPoint
        FROM Route r
        WHERE r.source = ?1
        """)
    List<String> getPickupPoints(
            String source);

    @Query("""
        SELECT DISTINCT r.dropPoint
        FROM Route r
        WHERE r.destination = ?1
        """)
    List<String> getDropPoints(
            String destination);
}