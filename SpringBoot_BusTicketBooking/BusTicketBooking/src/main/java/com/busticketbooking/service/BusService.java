package com.busticketbooking.service;

import com.busticketbooking.dto.BusDto;
import com.busticketbooking.dto.BusOperatorBusDto;
import com.busticketbooking.dto.BusOperatorResponseDto;
import com.busticketbooking.dto.BusResponseDto;
import com.busticketbooking.enums.BusType;
import com.busticketbooking.enums.SeatStatus;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.exception.UnauthorizedActionException;
import com.busticketbooking.mapper.BusMapper;
import com.busticketbooking.model.Bus;
import com.busticketbooking.model.BusOperator;
import com.busticketbooking.model.Seat;
import com.busticketbooking.repository.BusRepository;
import com.busticketbooking.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;
    private final BusOperatorService busOperatorService;
    private final SeatRepository seatRepository;

    public void addBus(
            BusDto dto,
            String email){

        BusOperator operator =
                busOperatorService.getByEmail(email);

        Bus bus =
                busMapper.mapDtoToEntity(dto);

        bus.setBusOperator(operator);

        busRepository.save(bus);

        String[] seatNumbers = {
                "A1","A2","A3","A4",
                "B1","B2","B3","B4",
                "C1","C2","C3","C4",
                "D1","D2","D3","D4"
        };

        for(String seatNo : seatNumbers){

            Seat seat = new Seat();

            seat.setSeatNumber(seatNo);

            seat.setBus(bus);

            seat.setSeatStatus(SeatStatus.AVAILABLE);

            seatRepository.save(seat);
        }
    }

    public List<Bus> getAll(){

        return busRepository.findAll();
    }

    public Bus getById(int busId){

        return busRepository.findById(busId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Bus ID"));
    }

    public void deleteById(
            int busId,
            String email){

        Bus bus = getById(busId);

        BusOperator operator =
                busOperatorService
                        .getByEmail(email);

        if(bus.getBusOperator()
                .getOperatorId()
                != operator.getOperatorId()){

            throw new UnauthorizedActionException(
                    "Bus does not belong to you");
        }

        busRepository.deleteById(busId);
    }

    public void update(
            int busId,
            Bus updatedBus,
            String email){

        Bus existingBus =
                getById(busId);

        BusOperator operator =
                busOperatorService
                        .getByEmail(email);

        if(existingBus.getBusOperator()
                .getOperatorId()
                != operator.getOperatorId()){

            throw new UnauthorizedActionException(
                    "Bus does not belong to you");
        }

        existingBus.setBusName(
                updatedBus.getBusName());

        existingBus.setBusNumber(
                updatedBus.getBusNumber());

        existingBus.setBusType(
                updatedBus.getBusType());

        existingBus.setTotalSeats(
                updatedBus.getTotalSeats());

        existingBus.setFareAmount(
                updatedBus.getFareAmount());

        busRepository.save(existingBus);
    }

    public List<Bus> getByBusType(
            BusType busType){

        return busRepository.findByBusType(busType);
    }


    public List<BusOperatorBusDto>
    getBusByOperatorEmail(String email){

        List<Bus> buses =
                busRepository
                        .findByBusOperatorEmail(email);

        return buses.stream()
                .map(busMapper::mapEntityToDto)
                .toList();
    }

    // pagination
    public Page<BusResponseDto> getAllBuses(
            int page,
            int size){

        Pageable pageable =
                PageRequest.of(page, size);

        return busRepository
                .findAll(pageable)
                .map(bus -> new BusResponseDto(

                        bus.getBusId(),

                        bus.getBusName(),

                        bus.getBusNumber(),

                        bus.getBusType(),

                        bus.getTotalSeats(),

                        bus.getFareAmount(),

                        new BusOperatorResponseDto(

                                bus.getBusOperator().getOperatorId(),

                                bus.getBusOperator().getOperatorName(),

                                bus.getBusOperator().getEmail(),

                                bus.getBusOperator().getCompanyName(),

                                bus.getBusOperator().getPhone()
                        )
                ));
    }
    public long getBusCount(){
        return busRepository.count();
    }
}