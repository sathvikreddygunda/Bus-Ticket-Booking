package com.controller;

import com.config.HibernateConfig;
import com.model.Booking;
import com.service.BookingService;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

import com.exception.ResourceNotFoundException;

public class MainClass {

    public static void main(String[] args) {

        HibernateConfig.getSessionFactory();

        System.out.println("Works");

        Session session = HibernateConfig
                        .getSessionFactory()
                        .openSession();

        Scanner sc = new Scanner(System.in);

        BookingService bookingService = new BookingService(session);

        while(true){

            System.out.println("1. Add Booking"); // Create
            System.out.println("2. Delete Booking by id"); //Delete
            System.out.println("3. Fetch all Bookings"); // Read
            System.out.println("4. Fetch Booking by id"); // Read
            System.out.println("0. Exit ");

            int op = sc.nextInt();

            if(op == 0)
                break;

            switch(op){

                case 1:

                    // Take input from user

                    Booking booking = new Booking();
                    sc.nextLine();

                    System.out.println("Enter Passenger Name");
                    booking.setPassengerName(
                            sc.nextLine()
                    );

                    System.out.println("Enter Source");
                    booking.setSource(
                            sc.nextLine()
                    );

                    System.out.println("Enter Destination");
                    booking.setDestination(
                            sc.nextLine()
                    );

                    System.out.println("Enter Fare Amount");
                    booking.setFareAmount(
                            sc.nextDouble()
                    );

                    bookingService.insert(booking);

                    System.out.println("Booking Added");

                    break;

                case 2:

                    System.out.println("Enter Booking ID to delete record");

                    int id = sc.nextInt();

                    try {

                        bookingService.deleteRecord(id);

                        System.out.println("Record deleted");
                    }

                    catch(ResourceNotFoundException e){

                        System.out.println(e.getMessage());
                    }

                    break;

                case 3:

                    System.out.println("----------All Bookings----------");
                    List<Booking> list = bookingService.getAllBookings();
                    list.forEach(System.out::println);
                    break;

                case 4:

                    System.out.println("Enter Booking ID");
                    id = sc.nextInt();
                    try{

                        Booking booking1 = bookingService.getById(id);
                        System.out.println(booking1);
                    }

                    catch(ResourceNotFoundException e){

                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("invalid option. try again");
                    break;
            }
        }

        sc.close();

        session.close();
    }
}