package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource(){

        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

        ds.setUrl("jdbc:mysql://localhost:3306/bus_ticket_booking");

        ds.setUsername("root");

        ds.setPassword("1234");

        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){

        return new JdbcTemplate(
                dataSource()
        );
    }
}