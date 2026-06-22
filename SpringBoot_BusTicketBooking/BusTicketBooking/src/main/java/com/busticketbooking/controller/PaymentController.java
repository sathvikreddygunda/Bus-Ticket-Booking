package com.busticketbooking.controller;

import com.busticketbooking.dto.PaymentDto;
import com.busticketbooking.model.Payment;
import com.busticketbooking.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor

public class PaymentController {

    private final PaymentService paymentService;

    /*
     Create Payment
     */
    @PostMapping("/add/{bookingId}")
    public void addPayment(

            @Valid
            @RequestBody PaymentDto dto,
            @PathVariable int bookingId){
        paymentService.addPayment(
                dto,
                bookingId);
    }

    @GetMapping("/all")
    public List<Payment> getAll(){

        return paymentService.getAll();
    }
}