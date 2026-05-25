package com.busticketbooking.service;



import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.model.Booking;
import com.busticketbooking.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor

public class BookingService {
    private final BookingRepository bookingRepository;

    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }
    public void addBooking(Booking booking){
        bookingRepository.save(booking);
    }
    public Booking getById(int id){
        return bookingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Booking ID"));

    }

    public void deleteById(int id){
        getById(id);
        bookingRepository.deleteById(id);
    }
    public void update(int id, Booking updatedBooking){
        Booking existingBooking = getById(id);

        existingBooking.setPassengerName(updatedBooking.getPassengerName());

        existingBooking.setSource(updatedBooking.getSource());

        existingBooking.setDestination(updatedBooking.getDestination());

        existingBooking.setFareAmount(updatedBooking.getFareAmount());

        existingBooking.setBookingStatus(updatedBooking.getBookingStatus());

        existingBooking.setBusType(updatedBooking.getBusType());

        bookingRepository.save(existingBooking);

    }

}
