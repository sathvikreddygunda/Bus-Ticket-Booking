package com.busticketbooking.controller;

import com.busticketbooking.dto.BusDto;
import com.busticketbooking.dto.BusOperatorBusDto;
import com.busticketbooking.dto.BusResponseDto;
import com.busticketbooking.enums.BusType;
import com.busticketbooking.model.Bus;
import com.busticketbooking.service.BusService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/bus")
@CrossOrigin(origins = "http://localhost:5173")

public class BusController {

    private final BusService busService;

    @PostMapping("/add")
    public void addBus(

            @Valid
            @RequestBody BusDto dto,

            Principal principal){

        busService.addBus(
                dto,
                principal.getName());
    }

    @GetMapping("/all")
    public List<Bus> getAll(){

        return busService.getAll();
    }

    @GetMapping("/get-one/{busId}")
    public ResponseEntity<Object> getById(
            @PathVariable int busId){

        return ResponseEntity
                .ok(busService.getById(busId));
    }

    @DeleteMapping("/delete/{busId}")
    public void deleteById(
            @PathVariable int busId,
            Principal principal){

        busService.deleteById(
                busId,
                principal.getName());
    }

    @PutMapping("/update/{busId}")
    public void update(

            @PathVariable int busId,

            @RequestBody Bus updatedBus,

            Principal principal){

        busService.update(
                busId,
                updatedBus,
                principal.getName());
    }

    @GetMapping("/type")
    public List<Bus> getByBusType(
            @RequestParam BusType busType){

        return busService.getByBusType(busType);
    }

    @GetMapping("/operator")
    public List<BusOperatorBusDto>
    getBusByOperatorEmail(
            @RequestParam String email){

        return busService
                .getBusByOperatorEmail(email);
    }

    // pagination

    @GetMapping("/all-page")
    public Page<BusResponseDto> getAllBuses(

            @RequestParam int page,

            @RequestParam int size){

        return busService.getAllBuses(page, size);
    }
    @GetMapping("/my-buses")
    public List<BusOperatorBusDto>
    getMyBuses(
            Principal principal){
        System.out.println(
                "MY BUSES API HIT -> "
                        + principal.getName());

        return busService
                .getBusByOperatorEmail(
                        principal.getName());
    }
    @GetMapping("/count")
    public long getBusCount(){

        return busService.getBusCount();
    }

}