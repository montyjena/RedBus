package com.redBus.operator.service;


import com.redBus.operator.entity.BusOperator;
import com.redBus.operator.entity.TicketCost;
import com.redBus.operator.payload.BusOperatorDto;
import com.redBus.operator.repository.BusOperatorRepository;

import com.redBus.operator.repository.TicketCostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusOperatorServiceImpl implements BusOperatorService{
    @Autowired
    private BusOperatorRepository busOperatorRepository;

    private TicketCostRepository ticketCostRepository;

    private ModelMapper mapper;

    public BusOperatorServiceImpl(BusOperatorRepository busOperatorRepository,TicketCostRepository ticketCostRepository, ModelMapper mapper) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository=ticketCostRepository;
        this.mapper = mapper;
    }

    @Override
    public BusOperatorDto scheduleBus(BusOperatorDto busOperatorDto){
        BusOperator busOperator=mapToEntity(busOperatorDto);


        TicketCost ticketCost = new TicketCost();
        ticketCost.setTicketId(busOperatorDto.getTicketCost().getTicketId());
        ticketCost.setCost(busOperatorDto.getTicketCost().getCost());
        ticketCost.setCode(busOperatorDto.getTicketCost().getCode());
        ticketCost.setDiscountAmount(busOperatorDto.getTicketCost().getDiscountAmount());
        busOperatorDto.setTicketCost(ticketCost);

        String busId = UUID.randomUUID().toString();
        busOperator.setBusId(busId);
        BusOperator savedBusSchedule=busOperatorRepository.save(busOperator);
        return mapToDto(savedBusSchedule);

    }
    BusOperator mapToEntity(BusOperatorDto busOperatorDto){
        BusOperator busOperator=mapper.map(busOperatorDto, BusOperator.class);
        return busOperator;
    }
    BusOperatorDto mapToDto(BusOperator busOperator){
        BusOperatorDto busOperatorDto=mapper.map(busOperator, BusOperatorDto.class);
        return busOperatorDto;
    }

    // Implement the methods of the BusOperatorService interface here

}
