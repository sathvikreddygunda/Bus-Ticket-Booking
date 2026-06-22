package com.busticketbooking.mapper;

import com.busticketbooking.dto.CustomerDto;
import com.busticketbooking.dto.CustomerResponseDto;
import com.busticketbooking.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    /*
    DTO -> Entity
    */
    public Customer mapDtoToEntity(
            CustomerDto dto){

        Customer customer =
                new Customer();

        customer.setCustomerName(
                dto.customerName());

        customer.setPhone(
                dto.phone());

        customer.setAddress(
                dto.address());

        return customer;
    }

    /*
    Entity -> Response DTO
    */
    public CustomerResponseDto
    mapEntityToDto(
            Customer customer){

        return new CustomerResponseDto(

                customer.getCustomerId(),

                customer.getCustomerName(),

                customer.getUser().getEmail(),

                customer.getPhone(),

                customer.getAddress()
        );
    }
}