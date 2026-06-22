package com.busticketbooking.service;

import com.busticketbooking.dto.CustomerResponseDto;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.mapper.CustomerMapper;
import com.busticketbooking.model.Customer;
import com.busticketbooking.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    /*
    Get Customer By Email
    */
    public Customer getByEmail(
            String email){

        return customerRepository
                .findByUserEmail(email);
    }

    /*
    Get Customer By ID
    */
    public Customer getById(
            int customerId){

        return customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Customer ID"));
    }

    /*
    Get Customer By ID DTO
    */
    public CustomerResponseDto
    getCustomerById(
            int customerId){

        return customerMapper
                .mapEntityToDto(
                        getById(customerId));
    }

    /*
    Get All Customers
    */
    public List<CustomerResponseDto>
    getAllCustomers(){

        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::mapEntityToDto)
                .toList();
    }

    /*
    Logged-In Customer Profile
    */
    public CustomerResponseDto
    getMyProfile(
            String email){

        Customer customer =
                getByEmail(email);

        return customerMapper
                .mapEntityToDto(customer);
    }
    public long getCustomerCount(){

        return customerRepository.count();
    }
}