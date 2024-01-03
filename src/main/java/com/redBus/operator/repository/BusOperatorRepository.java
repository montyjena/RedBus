package com.redBus.operator.repository;

import com.redBus.operator.entity.BusOperator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BusOperatorRepository extends JpaRepository<BusOperator,String> {
    List<BusOperator> findByDepartureCityAndArrivalCityAndDepartureDate(String departureCity, String arrivalCity, Date departureDate);


    Optional<BusOperator> findByBusIdAndAvailableSeatsGreaterThanEqual(String busId, int i);
}
