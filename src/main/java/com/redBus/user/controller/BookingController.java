package com.redBus.user.controller;

import com.redBus.user.payload.BookingDetailsDto;
import com.redBus.user.payload.PassengerDetails;
import com.redBus.user.service.BookingService;
import com.redBus.util.EmailService;
import com.redBus.util.PdfGenerationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;
    private EmailService emailService;
    private PdfGenerationService pdfGenerationService;

    public BookingController(
            BookingService bookingService,
            EmailService emailService,
            PdfGenerationService pdfGenerationService
    ) {
        this.bookingService = bookingService;
        this.emailService = emailService;
        this.pdfGenerationService = pdfGenerationService;
    }

    // http://localhost:8080/api/bookings?busId=&ticketId=
    @PostMapping
    public ResponseEntity<BookingDetailsDto> bookBus(
            @RequestParam("busId") String busId,
            @RequestParam("ticketId") String ticketId,
            @RequestBody PassengerDetails passengerDetails
    ) {
        BookingDetailsDto booking = bookingService.createBooking(busId, ticketId, passengerDetails);
        if (booking != null) {
            // Generate PDF with booking details
            byte[] pdfBytes = pdfGenerationService.generatePdfFromBookingDetails(booking);

            // Send confirmation email with PDF attachment
            emailService.sendEmailWithAttachment(
                    passengerDetails.getEmail(),
                    "Booking Confirmed. Booking Id: " + booking.getBookingId(),
                    "Your Booking is Confirmed\nName: " + passengerDetails.getFirstName() + " " + passengerDetails.getLastName(),
                    "booking_details.pdf", // Attachment filename
                    pdfBytes // PDF file content as byte array
            );
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

}
