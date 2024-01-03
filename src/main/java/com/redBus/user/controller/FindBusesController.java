package com.redBus.user.controller;

import com.redBus.operator.entity.BusOperator;
import com.redBus.operator.service.BusOperatorService;
import com.redBus.user.payload.BusListDto;
import com.redBus.user.service.SearchBusesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FindBusesController {
    @Autowired
    private SearchBusesService searchBusesService;
    //http://localhost:8080/api/user/searchBuses?departureCity=&arrivalCity=City&departureDate=01.01.2023
    @GetMapping("/searchBuses")
    public List<BusListDto> searchBuses(@RequestParam("departureCity") String departureCity,
                                         @RequestParam("arrivalCity")String arrivalCity,
                                         @RequestParam("departureDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date departureDate){
        List<BusListDto> busListDtos = searchBusesService.searchBusBy(departureCity, arrivalCity, departureDate);
        return busListDtos;
    }

}
