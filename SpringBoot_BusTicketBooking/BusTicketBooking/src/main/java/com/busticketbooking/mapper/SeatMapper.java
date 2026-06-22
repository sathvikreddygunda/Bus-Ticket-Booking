package com.busticketbooking.mapper;

import com.busticketbooking.dto.SeatDto;
import com.busticketbooking.model.Seat;
import org.springframework.stereotype.Component;

@Component

public class SeatMapper {

    /*
    DTO → Entity Conversion
    */

    public Seat mapDtoToEntity(
            SeatDto dto){

        Seat seat = new Seat();

        seat.setSeatNumber(dto.seatNumber());

        seat.setSeatStatus(dto.seatStatus());

        return seat;
    }
}