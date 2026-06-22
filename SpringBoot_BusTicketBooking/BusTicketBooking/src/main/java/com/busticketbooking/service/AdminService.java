package com.busticketbooking.service;

import com.busticketbooking.dto.AdminStatsDto;
import com.busticketbooking.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private final BookingRepository bookingRepository;
    private CustomerRepository customerRepository;

    private BusOperatorRepository operatorRepository;

    private BusRepository busRepository;

    private RouteRepository routeRepository;

    public AdminStatsDto getStats() {

        Long customerCount =
                customerRepository.count();

        Long operatorCount =
                operatorRepository.count();

        Long busCount =
                busRepository.count();

        Long routeCount =
                routeRepository.count();

        Long bookingCount =
                bookingRepository.count();

        Long pendingOperatorCount =
                (long) operatorRepository
                        .findByStatus(
                                com.busticketbooking.enums.OperatorStatus.PENDING
                        )
                        .size();

        return new AdminStatsDto(

                customerCount,
                operatorCount,
                busCount,
                routeCount,
                bookingCount,
                pendingOperatorCount
        );
    }
}