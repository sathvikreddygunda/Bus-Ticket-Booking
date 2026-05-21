package com.controller;

import com.config.AppConfig;
import com.model.Booking;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        JdbcTemplate jdbcTemplate =
                context.getBean(JdbcTemplate.class);

        Scanner sc = new Scanner(System.in);

        while(true) {

            System.out.println("1. Add Booking");
            System.out.println("2. Delete Booking");
            System.out.println("3. View All Bookings");
            System.out.println("4. Fetch Booking By ID");
            System.out.println("0. Exit");

            int op = sc.nextInt();

            if (op == 0)
                break;

            switch (op) {

                case 1:

                    sc.nextLine();

                    System.out.println("Enter Passenger Name");

                    String name = sc.nextLine();

                    System.out.println("Enter Source");

                    String source = sc.nextLine();

                    System.out.println("Enter Destination");

                    String destination = sc.nextLine();

                    System.out.println("Enter Fare Amount");

                    double fare = sc.nextDouble();

                    jdbcTemplate.update(
                            "insert into booking(" +
                                    "passengerName," +
                                    "source," +
                                    "destination," +
                                    "fareAmount) values(?,?,?,?)",
                            name,
                            source,
                            destination,
                            fare
                    );

                    System.out.println("Booking Added");
                    break;

                case 2:

                    System.out.println("Enter Booking ID");

                    int id = sc.nextInt();

                    jdbcTemplate.update(
                            "delete from booking where id=?",
                            id
                    );

                    System.out.println("Booking Deleted");

                    break;

                case 3:

                    List<Map<String, Object>> list =

                            jdbcTemplate.queryForList(
                                    "select * from booking"
                            );

                    list.forEach(System.out::println);

                    break;

                case 4:

                    System.out.println("Enter Booking ID");

                    id = sc.nextInt();

                    Map<String, Object> row =

                            jdbcTemplate.queryForMap(
                                    "select * from booking where id=?",
                                    id
                            );

                    System.out.println(row);

                    break;

                case 5:

                    System.out.println("Enter Booking ID");

                    id = sc.nextInt();

                    sc.nextLine();

                    System.out.println("Enter New Passenger Name");

                    String newName =
                            sc.nextLine();

                    jdbcTemplate.update(
                            "update booking set passengerName=? where id=?",
                            newName,
                            id
                    );

                    System.out.println("Booking Updated");

                    break;

                default:

                    System.out.println("Invalid Option");
            }

        }

        context.close();
        sc.close();
    }
}