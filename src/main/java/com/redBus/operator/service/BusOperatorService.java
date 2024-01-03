package com.redBus.operator.service;


import com.redBus.operator.payload.BusOperatorDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface BusOperatorService {

    BusOperatorDto scheduleBus(@RequestBody BusOperatorDto busOperatorDto);
}