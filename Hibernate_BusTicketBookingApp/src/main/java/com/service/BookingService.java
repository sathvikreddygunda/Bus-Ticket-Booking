package com.service;

import com.model.Booking;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import com.exception.ResourceNotFoundException;

public class BookingService {

    private final Session session;

    public BookingService(Session session) {
        this.session = session;
    }

    // INSERT
    public void insert(Booking booking) {
        // begin the transaction
        Transaction tx = session.beginTransaction();
        // DB Operation
        session.persist(booking);
        tx.commit();
    }

    // DELETE
    public void deleteRecord(int id) {

        Transaction tx = session.beginTransaction();

        // Validate ID
        Booking booking = session.find(Booking.class, id);
        if(booking == null) {
            tx.commit();
            throw new ResourceNotFoundException("Invalid ID given..");
        }
        // Remove object
        session.remove(booking);
        tx.commit();
    }

    // FETCH BY ID
    public Booking getById(int id) {
        Transaction tx = session.beginTransaction();
        Booking booking = session.find(Booking.class, id);
        tx.commit();
        if(booking == null)
            throw new ResourceNotFoundException("Invalid ID given..");
        return booking;
    }

    // FETCH ALL
    public List<Booking> getAllBookings() {
        Transaction tx = session.beginTransaction();
        List<Booking> list =
                session.createQuery("from Booking", Booking.class).list();
        tx.commit();
        return list;
    }
}

/*
SQL:
select * from booking

HQL:
from Booking
*/