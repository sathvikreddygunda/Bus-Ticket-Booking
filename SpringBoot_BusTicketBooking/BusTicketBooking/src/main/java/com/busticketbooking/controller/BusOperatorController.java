package com.busticketbooking.controller;

import com.busticketbooking.dto.BusOperatorDto;
import com.busticketbooking.dto.BusOperatorResponseDto;
import com.busticketbooking.model.BusOperator;
import com.busticketbooking.service.BusOperatorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/operator")
@CrossOrigin(origins = "http://localhost:5173")
public class BusOperatorController {

    private final BusOperatorService busOperatorService;

    // add busoperator

    @PostMapping("/register")
    public void addOperator(
            @Valid
            @RequestBody BusOperatorDto dto){

        busOperatorService.addOperator(dto);
    }
    // get AllOperators
    @GetMapping("/all")
    public List<BusOperatorResponseDto>
    getAll(){

        return busOperatorService.getAllOperators();
    }
    // get operator by id

    @GetMapping("/get-one/{operatorId}")
    public BusOperatorResponseDto
    getById(
            @PathVariable int operatorId){

        return busOperatorService.getOperatorById(operatorId);
    }

    @DeleteMapping("/delete/{operatorId}")
    public void deleteById(
            @PathVariable int operatorId){

        busOperatorService.deleteById(operatorId);
    }

    @PutMapping("/update/{operatorId}")
    public void update(
            @PathVariable int operatorId,
            @RequestBody BusOperator updatedOperator){

        busOperatorService.update(
                operatorId,
                updatedOperator);
    }

    @GetMapping("/by-email")
    public BusOperator getByEmail(
            @RequestParam String email){

        return busOperatorService.getByEmail(email);
    }
    @GetMapping("/count")
    public long getOperatorCount(){

        return busOperatorService
                .getOperatorCount();
    }

    @GetMapping("/pending")
    public List<BusOperator>
    getPendingOperators(){

        return busOperatorService
                .getPendingOperators();
    }
    @PutMapping("/approve/{id}")
    public void approveOperator(
            @PathVariable int id){

        busOperatorService
                .approveOperator(id);
    }
    @PutMapping("/reject/{id}")
    public void rejectOperator(
            @PathVariable int id){

        busOperatorService
                .rejectOperator(id);
    }
}