package com.redBus.user.service;

import com.redBus.operator.entity.BusOperator;
import com.redBus.operator.entity.TicketCost;
import com.redBus.operator.repository.BusOperatorRepository;
import com.redBus.operator.repository.TicketCostRepository;
import com.redBus.user.entity.Booking;
import com.redBus.user.payload.BookingDetailsDto;
import com.redBus.user.payload.PassengerDetails;
import com.redBus.user.repository.BookingRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {
    @Value("${stripe.api.key}") // Inject Stripe secret key from application.properties
    private String stripeApiKey;
    private BusOperatorRepository busOperatorRepository;
    private TicketCostRepository ticketCostRepository;
    private BookingRepository bookingRepository;

    @Autowired
    public BookingService(
            BusOperatorRepository busOperatorRepository,
            TicketCostRepository ticketCostRepository,
            BookingRepository bookingRepository) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.bookingRepository = bookingRepository;
    }

    public BookingDetailsDto createBooking(String busId, String ticketId, PassengerDetails passengerDetails) {
        Optional<BusOperator> optionalBus = busOperatorRepository.findByBusIdAndAvailableSeatsGreaterThanEqual(busId, 1); // Assuming 1 seat per booking

        if (optionalBus.isPresent()) {
            BusOperator bus = optionalBus.get();
            int requiredSeats = 1; // Assuming 1 seat per booking

            if (bus.getAvailableSeats() >= requiredSeats) {
                // Update available seats
                bus.setAvailableSeats(bus.getAvailableSeats() - requiredSeats);
                busOperatorRepository.save(bus);

                // Fetch ticket cost
                Optional<TicketCost> optionalTicketCost = ticketCostRepository.findById(ticketId);
                if (optionalTicketCost.isPresent()) {
                    TicketCost ticketCost = optionalTicketCost.get();

                    // Create Booking
                    Booking booking = new Booking();
                    String bookingId = UUID.randomUUID().toString();
                    booking.setBookingId(bookingId);
                    booking.setToDestination(bus.getArrivalCity());
                    booking.setFromLocation(bus.getDepartureCity());
                    booking.setBusCompany(bus.getBusOperatorCompanyName());
                    booking.setPrice(ticketCost.getCost());
                    booking.setFirstName(passengerDetails.getFirstName());
                    booking.setLastName(passengerDetails.getLastName());
                    booking.setEmail(passengerDetails.getEmail());
                    booking.setMobile(passengerDetails.getMobile());
                    booking.setBusId(bus.getBusId());
                    booking.setTicketId(ticketCost.getTicketId());

                    Booking ticketCreatedDetails = bookingRepository.save(booking);

                    // Create BookingDetailsDto
                    BookingDetailsDto dto = new BookingDetailsDto();
                    dto.setBookingId(ticketCreatedDetails.getBookingId());
                    dto.setFirstName(ticketCreatedDetails.getFirstName());
                    dto.setLastName(ticketCreatedDetails.getLastName());
                    dto.setPrice(ticketCreatedDetails.getPrice());
                    dto.setEmail(ticketCreatedDetails.getEmail());
                    dto.setMobile(ticketCreatedDetails.getMobile());
                    dto.setBusCompany(ticketCreatedDetails.getBusCompany());
                    dto.setBusId(ticketCreatedDetails.getBusId());
                    dto.setTicketId(ticketCreatedDetails.getTicketId());
                    dto.setFromLocation(ticketCreatedDetails.getFromLocation());
                    dto.setToDestination(ticketCreatedDetails.getToDestination());
                    dto.setMessage("Booking Confirmed !!");
                    return dto;
                } else {
                    // Handle case where ticket details are not found
                }
            } else {
                // Handle case where seats are not available
                // Maybe throw an exception or return an error message
            }
        } else {
            // Handle case where bus or required seats are not found
        }
        return null;
    }



    public String createPaymentIntent(Integer amount) {
        Stripe.apiKey =stripeApiKey;
        try {
            PaymentIntent intent = PaymentIntent.create(
                    PaymentIntentCreateParams.builder()
                            .setCurrency("usd")
                            .setAmount((long) amount * 100) // Amount in cents
                            .build()
            );

            return generateResponse(intent.getClientSecret());
        } catch (StripeException e) {
            return generateResponse("Error creating PaymentIntent: " + e.getMessage());
        }
    }
    private String generateResponse(String clientSecret) {
        // Here, you can return the client secret or handle it as needed
        return "{\"clientSecret\":\"" + clientSecret + "\"}";
    }
}
