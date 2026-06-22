package com.busticketbooking.controller;

import com.busticketbooking.dto.CustomerResponseDto;
import com.busticketbooking.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;


    /*
    Get Customer By ID
    */
    @GetMapping("/get-one/{customerId}")
    public CustomerResponseDto getById(
            @PathVariable int customerId){

        return customerService
                .getCustomerById(customerId);
    }

    /*
    Get All Customers
    */
    @GetMapping("/all")
    public List<CustomerResponseDto> getAllCustomers(){

        return customerService
                .getAllCustomers();
    }

    /*
    Logged-In Customer Profile
    */
    @GetMapping("/me")
    public CustomerResponseDto getMyProfile(
            Principal principal){

        return customerService
                .getMyProfile(
                        principal.getName());
    }
    @GetMapping("/count")
    public long getCustomerCount(){

        return customerService
                .getCustomerCount();
    }
}