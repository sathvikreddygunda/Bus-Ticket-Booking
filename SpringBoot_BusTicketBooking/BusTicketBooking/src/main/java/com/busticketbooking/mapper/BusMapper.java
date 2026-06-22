package com.busticketbooking.mapper;

import com.busticketbooking.dto.BusDto;
import com.busticketbooking.dto.BusOperatorBusDto;
import com.busticketbooking.model.Bus;
import org.springframework.stereotype.Component;

@Component
public class BusMapper {

    public Bus mapDtoToEntity(BusDto dto){

        Bus bus = new Bus();

        bus.setBusName(dto.busName());
        bus.setBusNumber(dto.busNumber());
        bus.setBusType(dto.busType());
        bus.setTotalSeats(dto.totalSeats());
        bus.setFareAmount(dto.fareAmount());

        return bus;
    }

    public BusOperatorBusDto
    mapEntityToDto(Bus bus){

        return new BusOperatorBusDto(

                bus.getBusId(),
                bus.getBusName(),
                bus.getBusNumber(),
                bus.getBusType(),
                bus.getTotalSeats(),
                bus.getFareAmount(),

                bus.getBusOperator().getOperatorId(),
                bus.getBusOperator().getOperatorName(),
                bus.getBusOperator().getCompanyName()
        );
    }
}