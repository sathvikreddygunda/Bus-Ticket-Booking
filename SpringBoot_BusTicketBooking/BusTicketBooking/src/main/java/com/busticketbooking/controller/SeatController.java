package com.busticketbooking.controller;

import com.busticketbooking.dto.SeatDto;
import com.busticketbooking.dto.SeatResponseDto;
import com.busticketbooking.model.Seat;
import com.busticketbooking.service.SeatService;
import com.busticketbooking.enums.SeatStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/seat")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SeatController {

    private final SeatService seatService;

    /*
    Add Seat for Bus
    */
    @PostMapping("/add/{busId}")
    public void addSeat(

            @Valid
            @RequestBody SeatDto dto,

            @PathVariable int busId,

            Principal principal){

        seatService.addSeat(
                dto,
                busId,
                principal.getName());
    }

    @GetMapping("/all")
    public List<Seat> getAll(){

        return seatService.getAll();
    }

    @GetMapping("/by-bus/{busId}")
    public List<SeatResponseDto>
    getByBus(
            @PathVariable int busId){

        return seatService
                .getSeatsByBus(busId);
    }

    @GetMapping("/available")
    public List<Seat> getAvailableSeats(){

        return seatService.getAvailableSeats();
    }

    /*
    Change AVAILABLE ↔ BOOKED
    */
    @PutMapping("/update-status/{seatId}")
    public void updateSeatStatus(
            @PathVariable int seatId,
            @RequestParam SeatStatus status){
        seatService.updateSeatStatus(seatId, status);
    }
    // seats availability

    @GetMapping("/available/{busId}")
    public List<SeatResponseDto>
    getAvailableSeatsByBus(
            @PathVariable int busId){

        return seatService
                .getAvailableSeatsByBus(busId);
    }

}