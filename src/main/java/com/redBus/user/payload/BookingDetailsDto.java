package com.redBus.user.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDetailsDto {
    private String bookingId;
    private String busCompany;
    private String toDestination;
    private String fromLocation;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private double price;
    private String busId;
    private String ticketId;
    private String message;
}
