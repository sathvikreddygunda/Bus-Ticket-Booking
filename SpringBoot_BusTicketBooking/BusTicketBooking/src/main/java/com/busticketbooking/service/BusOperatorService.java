package com.busticketbooking.service;

import com.busticketbooking.dto.BusOperatorDto;
import com.busticketbooking.dto.BusOperatorResponseDto;
import com.busticketbooking.enums.OperatorStatus;
import com.busticketbooking.enums.Role;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.mapper.BusOperatorMapper;
import com.busticketbooking.model.BusOperator;
import com.busticketbooking.model.User;
import com.busticketbooking.repository.BusOperatorRepository;
import com.busticketbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class BusOperatorService {

    private final BusOperatorRepository busOperatorRepository;
    private final BusOperatorMapper busOperatorMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void addOperator(
            BusOperatorDto dto){

        BusOperator existing =
                busOperatorRepository
                        .findByEmail(dto.email());

        if(existing != null){

            throw new RuntimeException(
                    "Email already registered");
        }

        BusOperator operator =
                busOperatorMapper
                        .mapDtoToEntity(dto);

        operator.setPassword(
                passwordEncoder.encode(
                        dto.password()));
        operator.setStatus(
                OperatorStatus.APPROVED
        );

        busOperatorRepository.save(operator);
        User user =
                new User();

        user.setEmail(
                dto.email());

        user.setPassword(
                passwordEncoder.encode(
                        dto.password()));

        user.setRole(
                Role.OPERATOR);

        userRepository.save(
                user);
    }
    public List<BusOperator>
    getPendingOperators(){

        return busOperatorRepository
                .findByStatus(
                        OperatorStatus.PENDING
                );
    }

    public void approveOperator(
            int operatorId){

        BusOperator operator =
                getById(operatorId);

        operator.setStatus(
                OperatorStatus.APPROVED);

        busOperatorRepository.save(
                operator);
    }
    public void rejectOperator(
            int operatorId){

        BusOperator operator =
                getById(operatorId);

        operator.setStatus(
                OperatorStatus.REJECTED);

        busOperatorRepository.save(
                operator);
    }


    public List<BusOperator> getAll(){

        return busOperatorRepository.findAll();
    }

    public BusOperator getById(int operatorId){

        return busOperatorRepository.findById(operatorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Operator ID"));
    }

    public void deleteById(int operatorId){

        BusOperator operator =
                getById(operatorId);

        User user =
                userRepository.findByEmail(
                        operator.getEmail());

        if(user != null){

            userRepository.delete(user);

        }

        busOperatorRepository.delete(operator);
    }

    public void update(
            int operatorId,
            BusOperator updatedOperator){

        BusOperator existingOperator =
                getById(operatorId);

        existingOperator.setOperatorName(
                updatedOperator.getOperatorName()
        );

        existingOperator.setEmail(
                updatedOperator.getEmail());

        existingOperator.setPassword(
                passwordEncoder.encode(
                        updatedOperator.getPassword()));

        existingOperator.setCompanyName(
                updatedOperator.getCompanyName());

        existingOperator.setPhone(
                updatedOperator.getPhone());

        busOperatorRepository.save(existingOperator);
    }

    public BusOperator getByEmail(String email){

        return busOperatorRepository.findByEmail(email);
    }
    public List<BusOperatorResponseDto>
    getAllOperators(){

        return busOperatorRepository.findAll()
                .stream()
                .map(busOperatorMapper::mapEntityToDto)
                .toList();
    }
    public BusOperatorResponseDto
    getOperatorById(
            int operatorId){

        BusOperator operator =
                getById(operatorId);

        return busOperatorMapper
                .mapEntityToDto(operator);
    }

    /*
    Fetch Operator by Email
    */
    public BusOperator loadOperatorByEmail(
            String email){

        BusOperator operator =
                busOperatorRepository
                        .findByEmail(email);

        if(operator == null){

            throw new ResourceNotFoundException(
                    "Invalid Operator Email");
        }

        return operator;
    }
    public long getOperatorCount(){

        return busOperatorRepository.count();
    }
}