package com.redBus.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Booking {
    @Id
    @Column(name = "booking_id")
    private String bookingId;
    @Column(name = "bus_id")
    private String busId;
    @Column(name = "ticket_id")
    private String ticketId;
    @Column(name = "bus_company")
    private String busCompany;

    private String toDestination;
    private String fromLocation;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    private String email;
    private String mobile;
    private double price;


}
