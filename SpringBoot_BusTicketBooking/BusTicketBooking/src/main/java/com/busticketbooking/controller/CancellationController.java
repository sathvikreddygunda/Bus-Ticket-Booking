package com.busticketbooking.controller;

import com.busticketbooking.model.Cancellation;
import com.busticketbooking.service.CancellationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
Controller Layer

Handles Cancellation APIs
*/

@RestController
@RequestMapping("/api/cancellation")
@AllArgsConstructor

public class CancellationController {

    private final CancellationService cancellationService;

    @PostMapping("/add/{bookingId}")
    public void addCancellation(
            @PathVariable int bookingId,
            Principal principal){

        cancellationService.addCancellation(
                bookingId,
                principal.getName());
    }

    @GetMapping("/all")
    public List<Cancellation> getAll(){

        return cancellationService.getAll();
    }

    @GetMapping("/get-one/{cancellationId}")
    public Cancellation getById(
            @PathVariable int cancellationId){
        return cancellationService
                .getById(cancellationId);
    }
}