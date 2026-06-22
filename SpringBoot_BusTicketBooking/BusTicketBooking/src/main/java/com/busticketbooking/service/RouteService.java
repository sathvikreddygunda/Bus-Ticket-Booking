package com.busticketbooking.service;

import com.busticketbooking.dto.RouteDto;
import com.busticketbooking.exception.ResourceNotFoundException;
import com.busticketbooking.exception.UnauthorizedActionException;
import com.busticketbooking.mapper.RouteMapper;
import com.busticketbooking.model.Bus;
import com.busticketbooking.model.Route;
import com.busticketbooking.repository.RouteRepository;
import lombok.AllArgsConstructor;
import com.busticketbooking.dto.RouteResponseDto;
import com.busticketbooking.model.BusOperator;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor

public class RouteService {

    private final RouteRepository routeRepository;
    private final BusOperatorService
            busOperatorService;

    private final RouteMapper routeMapper;

    private final BusService busService;

    /*
    Add Route with Bus
    */

    public void addRoute(
            RouteDto dto,
            int busId,
            String email){

        Bus bus =
                busService.getById(busId);

        BusOperator operator =
                busOperatorService
                        .getByEmail(email);

        if(bus.getBusOperator()
                .getOperatorId()
                != operator.getOperatorId()){

            throw new UnauthorizedActionException(
                    "Bus does not belong to you");
        }

        Route route =
                routeMapper
                        .mapDtoToEntity(dto);

        route.setBus(bus);

        routeRepository.save(route);
    }

    public List<Route> getAll(){

        return routeRepository.findAll();
    }

    public Route getById(
            int routeId){

        return routeRepository
                .findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Route ID"));
    }

    public void deleteById(
            int routeId,
            String email){

        Route route =
                getById(routeId);

        BusOperator operator =
                busOperatorService
                        .getByEmail(email);

        if(route.getBus()
                .getBusOperator()
                .getOperatorId()
                != operator.getOperatorId()){

            throw new UnauthorizedActionException(
                    "Route does not belong to you");
        }

        routeRepository.deleteById(routeId);
    }

    public List<Route> getBySource(
            String source){

        return routeRepository
                .findBySource(source);
    }

    public List<Route> getByDestination(
            String destination){

        return routeRepository
                .findByDestination(destination);
    }

    /*
Fetch Logged-In Operator Routes
*/
    public List<RouteResponseDto>
    getMyRoutes(String email){

        // Fetch Operator

        BusOperator operator =
                busOperatorService
                        .getByEmail(email);

        // Fetch all routes
        // belonging to operator buses

        return routeRepository
                .findByBusBusOperatorOperatorId(
                        operator.getOperatorId())
                .stream()
                .map(routeMapper::mapEntityToDto)
                .toList();
    }

    // search route from source -> destination

    public List<RouteResponseDto> searchRoutes(
            String source,
            String destination,
            LocalDate journeyDate){

        return routeRepository
                .searchRoutesJPQL(
                        source,
                        destination,
                        journeyDate)
                .stream()
                .map(route -> new RouteResponseDto(

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

                ))
                .toList();
    }

    /*
    Fetch All Routes
    with Pagination
    */
    public Page<RouteResponseDto>
    getAllRoutes(
            int page,
            int size){

        Pageable pageable =
                PageRequest.of(page, size);

        return routeRepository
                .findAll(pageable)
                .map(
                        routeMapper::mapEntityToDto);
    }
    public List<String> searchSources(
            String keyword){

        return routeRepository
                .searchSources(keyword);
    }

    public List<String> searchDestinations(
            String keyword){

        return routeRepository
                .searchDestinations(keyword);
    }
    public List<String> getPickupPoints(
            String source){

        return routeRepository
                .getPickupPoints(source);
    }

    public List<String> getDropPoints(
            String destination){

        return routeRepository
                .getDropPoints(destination);
    }
    public long getRouteCount(){

        return routeRepository.count();
    }
    public void update(

            int routeId,

            Route updatedRoute,

            String email){

        Route existingRoute =
                getById(routeId);

        existingRoute.setSource(
                updatedRoute.getSource());

        existingRoute.setDestination(
                updatedRoute.getDestination());

        existingRoute.setPickupPoint(
                updatedRoute.getPickupPoint());

        existingRoute.setDropPoint(
                updatedRoute.getDropPoint());

        existingRoute.setJourneyDate(
                updatedRoute.getJourneyDate());

        existingRoute.setDepartureTime(
                updatedRoute.getDepartureTime());

        existingRoute.setArrivalTime(
                updatedRoute.getArrivalTime());

        routeRepository.save(
                existingRoute);
    }
}