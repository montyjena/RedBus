package com.redBus.operator.controller;
import com.redBus.operator.payload.BusOperatorDto;
import com.redBus.operator.service.BusOperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bus-operator")
public class BusOperatorController {

    private BusOperatorService busOperatorService;

    public BusOperatorController(BusOperatorService busOperatorService) {
        this.busOperatorService = busOperatorService;
    }


    //http://localhost:8080/api/bus-operator
    @PostMapping
    public ResponseEntity<BusOperatorDto> scheduleBus(@RequestBody BusOperatorDto busOperatorDto) {
        BusOperatorDto dto = busOperatorService.scheduleBus(busOperatorDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }


}

