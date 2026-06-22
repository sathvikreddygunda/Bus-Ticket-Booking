package com.busticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatsDto {

    private Long customerCount;

    private Long operatorCount;

    private Long busCount;

    private Long routeCount;

    private Long bookingCount;

    private Long pendingOperatorCount;
}