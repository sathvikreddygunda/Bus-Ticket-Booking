package com.busticketbooking.controller;


import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.model.Booking;
import com.busticketbooking.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/all")
    public List<Booking> getAll(){
        return bookingService.getAll();

    }
    @PostMapping("/add")
    public void addBooking(
            @RequestBody Booking booking){
        bookingService.addBooking(booking);
    }
    @GetMapping("/get-one/{id}")
    public ResponseEntity<Object> getById(
            @PathVariable int id){
        try{
            Booking booking = bookingService.getById(id);
            return ResponseEntity
                    .ok(booking);
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(
            @PathVariable int id){
                try{
                    bookingService.deleteById(id);
                    return ResponseEntity
                            .ok()
                            .build();
                }
                catch(ResourceNotFoundException e){
                    return ResponseEntity
                            .badRequest()
                            .body(e.getMessage());
                }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(
            @PathVariable int id,
            @RequestBody Booking updateBooking) {
        try {
            bookingService.update(
                    id,
                    updateBooking);
            return ResponseEntity
                    .ok()
                    .build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }


    }

}
