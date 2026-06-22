package com.busticketbooking.controller;

import com.busticketbooking.dto.RouteDto;
import com.busticketbooking.dto.RouteResponseDto;
import com.busticketbooking.model.Route;
import com.busticketbooking.service.RouteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/route")
@CrossOrigin(origins = "http://localhost:5173")

public class RouteController {

    private final RouteService routeService;

    // search routes from source -> destination.

    @GetMapping("/search")
    public List<RouteResponseDto> searchRoutes(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam LocalDate journeyDate){

        return routeService.searchRoutes(
                source,
                destination,
                journeyDate);
    }

    /*
    Add Route with Bus
    */

    @PostMapping("/add/{busId}")
    public void addRoute(

            @Valid
            @RequestBody RouteDto dto,

            @PathVariable int busId,

            Principal principal){

        routeService.addRoute(
                dto,
                busId,
                principal.getName());
    }

    // get allRoutes
    @GetMapping("/all")

    public List<Route> getAll()
    {

        return routeService.getAll();
    }
    
    @GetMapping("/all/v2")
    public List<Route> getAllV2() {

        return routeService.getAll();

    }

    /*
    Get All Routes
    with Pagination

    Example:
    GET
    /api/route/all-page?page=0&size=5
    */
    @GetMapping("/all-page")
    public Page<RouteResponseDto>
    getAllRoutes(

            @RequestParam int page,

            @RequestParam int size){

        return routeService
                .getAllRoutes(
                        page,
                        size);
    }

    // get routeById

    @GetMapping("/get-one/{routeId}")
    public Route getById(
            @PathVariable int routeId){
        return routeService.getById(routeId);
    }

    // delete routeById
    @DeleteMapping("/delete/{routeId}")
    public void deleteById(
            @PathVariable int routeId,
            Principal principal){

        routeService.deleteById(
                routeId,
                principal.getName());
    }

    // get routeBySource
    @GetMapping("/by-source")

    public List<Route> getBySource(@RequestParam String source){
        return routeService
                .getBySource(source);
    }
    // get routebyDestination

    @GetMapping("/by-destination")
    public List<Route> getByDestination(@RequestParam String destination){
        return routeService
                .getByDestination(destination);
    }

    /*
Get Logged-In Operator Routes
*/
    @GetMapping("/my-routes")
    public List<RouteResponseDto>
    getMyRoutes(
            Principal principal){

        return routeService
                .getMyRoutes(
                        principal.getName());
    }
    @GetMapping("/sources")
    public List<String> searchSources(
            @RequestParam String keyword){

        return routeService
                .searchSources(keyword);
    }

    @GetMapping("/destinations")
    public List<String> searchDestinations(
            @RequestParam String keyword){

        return routeService
                .searchDestinations(keyword);
    }
    @GetMapping("/pickup-points")
    public List<String> getPickupPoints(
            @RequestParam String source){

        return routeService
                .getPickupPoints(source);
    }

    @GetMapping("/drop-points")
    public List<String> getDropPoints(
            @RequestParam String destination){

        return routeService
                .getDropPoints(destination);
    }
    @GetMapping("/count")
    public long getRouteCount(){

        return routeService.getRouteCount();
    }
    @PutMapping("/update/{routeId}")
    public void update(

            @PathVariable int routeId,

            @RequestBody Route updatedRoute,

            Principal principal){

        routeService.update(
                routeId,
                updatedRoute,
                principal.getName());
    }
}