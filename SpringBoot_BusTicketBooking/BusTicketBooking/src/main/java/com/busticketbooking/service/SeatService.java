package com.busticketbooking.service;

import com.busticketbooking.dto.SeatDto;
import com.busticketbooking.dto.SeatResponseDto;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.exception.UnauthorizedActionException;
import com.busticketbooking.mapper.SeatMapper;
import com.busticketbooking.enums.SeatStatus;
import com.busticketbooking.model.Bus;
import com.busticketbooking.model.Seat;
import com.busticketbooking.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.busticketbooking.model.BusOperator;

import java.util.List;

@Service
@AllArgsConstructor

public class SeatService {

    private final SeatRepository seatRepository;
    private final BusOperatorService busOperatorService;
    private final SeatMapper seatMapper;

    private final BusService busService;

    /*
    Add Seat with Bus relationship
    */
    public void addSeat(
            SeatDto dto,
            int busId,
            String email){

        // Fetch Bus

        Bus bus =
                busService.getById(busId);

        // Fetch Logged-in Operator

        BusOperator operator =
                busOperatorService
                        .getByEmail(email);

        // Ownership Validation

        if(bus.getBusOperator()
                .getOperatorId()
                != operator.getOperatorId()){

            throw new UnauthorizedActionException(
                    "Bus does not belong to you");
        }

        // DTO -> Entity

        Seat seat =
                seatMapper
                        .mapDtoToEntity(dto);

        seat.setBus(bus);

        seatRepository.save(seat);
    }

    public List<Seat> getAll(){

        return seatRepository.findAll();
    }

    public Seat getById(int seatId){

        return seatRepository
                .findById(seatId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Seat ID"));
    }

    public List<Seat> getByBus(int busId){

        return seatRepository
                .findByBusBusId(busId);
    }

    public List<Seat> getAvailableSeats(){

        return seatRepository
                .findBySeatStatus(
                        SeatStatus.AVAILABLE);
    }

    /*
    Update Seat Availability
    */
    public void updateSeatStatus(
            int seatId,
            SeatStatus seatStatus){

        Seat seat = getById(seatId);

        seat.setSeatStatus(seatStatus);

        seatRepository.save(seat);
    }
    // seats availability
    public List<SeatResponseDto> getAvailableSeatsByBus(
            int busId){

        return seatRepository
                .findByBusBusIdAndSeatStatus(
                        busId,
                        SeatStatus.AVAILABLE)
                .stream()
                .map(seat -> new SeatResponseDto(

                        seat.getSeatId(),

                        seat.getSeatNumber(),

                        seat.getSeatStatus().name(),

                        seat.getBus().getBusName(),

                        seat.getBus().getBusNumber()

                ))
                .toList();
    }

    public List<Seat> getAvailableSeatsByBusJPQL(
            int busId){

        return seatRepository
                .getAvailableSeatsJPQL(busId);
    }

    public List<SeatResponseDto>
    getSeatsByBus(int busId){

        return seatRepository
                .findByBusBusId(busId)
                .stream()
                .map(seat -> new SeatResponseDto(

                        seat.getSeatId(),

                        seat.getSeatNumber(),

                        seat.getSeatStatus().name(),

                        seat.getBus().getBusName(),

                        seat.getBus().getBusNumber()
                ))
                .toList();
    }

}