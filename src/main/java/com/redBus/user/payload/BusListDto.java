package com.redBus.user.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusListDto {
    private String busId;
    private String busNumber;
    private String busOperatorCompanyName;

    private String numberSeats;
    private String departureCity;
    private String arrivalCity;
    //@JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;
    //@JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private Date departureDate;
    // @JsonFormat(pattern = "dd/MM/yyyy")
    private Date arrivalDate;
    private double totalTravelTime;
    private String busType;
    private String amenities;
}
