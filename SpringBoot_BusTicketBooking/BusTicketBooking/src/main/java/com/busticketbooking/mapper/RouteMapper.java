package com.busticketbooking.mapper;

import com.busticketbooking.dto.RouteDto;
import com.busticketbooking.dto.RouteResponseDto;
import com.busticketbooking.model.Route;
import org.springframework.stereotype.Component;

@Component

public class RouteMapper {

    // DTO -> Entity
    public Route mapDtoToEntity(
            RouteDto dto){

        Route route = new Route();

        route.setSource(dto.source());
        route.setDestination(dto.destination());
        route.setPickupPoint(
                dto.pickupPoint());

        route.setDropPoint(
                dto.dropPoint());
        route.setJourneyDate(dto.journeyDate());
        route.setDepartureTime(dto.departureTime());
        route.setArrivalTime(dto.arrivalTime());

        return route;
    }

    /*
    Entity -> Response DTO
    */
    public RouteResponseDto
    mapEntityToDto(Route route){

        return new RouteResponseDto(

                route.getRouteId(),

                route.getBus().getBusId(),

                route.getSource(),

                route.getDestination(),

                route.getPickupPoint(),

                route.getDropPoint(),

                route.getJourneyDate(),

                route.getDepartureTime(),

                route.getArrivalTime(),

                route.getBus().getBusName(),

                route.getBus().getBusNumber(),

                route.getBus()
                        .getBusOperator()
                        .getOperatorName(),
                route.getBus().getFareAmount()
        );
    }

}