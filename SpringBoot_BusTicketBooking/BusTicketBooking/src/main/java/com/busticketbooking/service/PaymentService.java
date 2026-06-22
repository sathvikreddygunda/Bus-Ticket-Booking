package com.busticketbooking.service;

import com.busticketbooking.dto.PaymentDto;
import com.busticketbooking.enums.BookingStatus;
import com.busticketbooking.enums.PaymentStatus;
import com.busticketbooking.model.Booking;
import com.busticketbooking.model.Payment;
import com.busticketbooking.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;

    /*
     Create Payment for Booking
     */
    public void addPayment(
            PaymentDto dto,
            int bookingId){
        Booking booking =
                bookingService.getById(bookingId);

        if(booking.getBookingStatus()
                == BookingStatus.CANCELLED){

            throw new RuntimeException(
                    "Cannot pay for cancelled booking");
        }

        if(dto.amount()
                != booking.getTotalAmount()){

            throw new RuntimeException(
                    "Invalid Payment Amount");
        }

        Payment payment = new Payment();

        payment.setPaymentMethod(
                dto.paymentMethod());

        payment.setAmount(
                dto.amount());

        payment.setPaymentStatus(
                PaymentStatus.SUCCESS);

        payment.setBooking(booking);

        paymentRepository.save(payment);
    }

    public List<Payment> getAll(){

        return paymentRepository.findAll();
    }
}