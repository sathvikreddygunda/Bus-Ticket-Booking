package com.config;

import com.model.Booking;
import com.model.Bus;
import com.model.Route;
import com.model.User;
import com.model.Payment;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateConfig {

    // This class will manage ur DB connection
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        /*
        check if factory is null
        if sessionFactory is null,
        then give it properties to make DB connection
        */

        if(sessionFactory == null){

            // Map is used to save DB settings
            Configuration configuration =
                    new Configuration();

            // DB credentials , URLs and dialect

            configuration.setProperty(
                    "hibernate.connection.url",
                    "jdbc:mysql://localhost:3306/bus_ticket_booking?createDatabaseIfNotExist=true"
            );

            configuration.setProperty(
                    "hibernate.connection.username",
                    "root"
            );

            configuration.setProperty(
                    "hibernate.connection.password",
                    "1234"
            );

            configuration.setProperty(
                    "hibernate.connection.driver_class",
                    "com.mysql.cj.jdbc.Driver"
            );

            // set the dialect

            configuration.setProperty(
                    "hibernate.dialect",
                    "org.hibernate.dialect.MySQLDialect"
            );

            // If u want to hibernate to generate
            // DB tables on the fly

            configuration.setProperty(
                    "hibernate.hbm2ddl.auto",
                    "update"
            );

            // Add model classes

            configuration.addAnnotatedClass(
                    Booking.class
            );
            configuration.addAnnotatedClass(
                    Bus.class
            );

            configuration.addAnnotatedClass(
                    User.class
            );

            configuration.addAnnotatedClass(
                    Route.class
            );

            configuration.addAnnotatedClass(
                    Payment.class
            );

            // Build SessionFactory

            sessionFactory =
                    configuration.buildSessionFactory();
        }

        return sessionFactory;
    }

    public void closeFactory(){

        sessionFactory.close();
    }
}