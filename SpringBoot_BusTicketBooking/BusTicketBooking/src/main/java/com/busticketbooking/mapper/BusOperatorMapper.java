package com.busticketbooking.mapper;

import com.busticketbooking.dto.BusOperatorDto;
import com.busticketbooking.dto.BusOperatorResponseDto;
import com.busticketbooking.model.BusOperator;
import org.springframework.stereotype.Component;

@Component
public class BusOperatorMapper {

    public BusOperator mapDtoToEntity(
            BusOperatorDto dto){

        BusOperator operator = new BusOperator();

        operator.setOperatorName(dto.operatorName());
        operator.setEmail(dto.email());
        operator.setPassword(dto.password());
        operator.setCompanyName(dto.companyName());
        operator.setPhone(dto.phone());

        return operator;
    }
    public BusOperatorResponseDto
    mapEntityToDto(
            BusOperator operator){

        return new BusOperatorResponseDto(
                operator.getOperatorId(),
                operator.getOperatorName(),
                operator.getEmail(),
                operator.getCompanyName(),
                operator.getPhone()
        );
    }
}