package com.busticketbooking.service;

import com.busticketbooking.dto.UserDto;
import com.busticketbooking.enums.Role;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.model.User;
import com.busticketbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.busticketbooking.dto.RegisterDto;
import com.busticketbooking.model.Customer;
import com.busticketbooking.repository.CustomerRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;


    /*
    Register Customer
    Creates:
    1. User Account
    2. Customer Profile
    */
    public void register(
            RegisterDto dto){

        User user =
                new User();

        user.setEmail(
                dto.email());

        user.setPassword(
                passwordEncoder.encode(
                        dto.password()));

        user.setRole(
                Role.USER);

        User savedUser =
                userRepository.save(user);

        Customer customer =
                new Customer();

        customer.setUser(
                savedUser);

        customerRepository.save(
                customer);
    }

    /*
    Fetch User By ID
    */
    public User getById(
            int userId) {

        return userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid User ID"));
    }

    /*
    Delete User
    */
    public void deleteById(
            int userId) {

        getById(userId);

        userRepository.deleteById(
                userId);
    }

    /*
    Update User
    */
    public void update(
            int userId,
            User updatedUser) {

        User existingUser =
                getById(userId);

        existingUser.setEmail(
                updatedUser.getEmail());

        existingUser.setPassword(
                passwordEncoder.encode(
                        updatedUser.getPassword()));

        userRepository.save(
                existingUser);
    }

    /*
    Get User By Email
    */
    public User getByEmail(
            String email) {

        return userRepository
                .findByEmail(email);
    }

    /*
    Reset Password
    */
    public void resetPassword(
            String email,
            String newPassword) {

        User user =
                userRepository.findByEmail(
                        email);

        if(user == null){

            throw new ResourceNotFoundException(
                    "User not found");
        }

        user.setPassword(
                passwordEncoder.encode(
                        newPassword));

        userRepository.save(
                user);
    }

}