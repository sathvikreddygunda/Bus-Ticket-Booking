package com.busticketbooking.repository;

import com.busticketbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}