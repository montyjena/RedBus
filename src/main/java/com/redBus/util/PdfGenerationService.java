package com.redBus.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.redBus.user.payload.BookingDetailsDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGenerationService {

    public byte[] generatePdfFromBookingDetails(BookingDetailsDto bookingDetails) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Add booking details to the PDF
            document.add(new Paragraph("Booking Details"));
            document.add(new Paragraph("Booking ID: " + bookingDetails.getBookingId()));
            document.add(new Paragraph("Bus Company: " + bookingDetails.getBusCompany()));
            document.add(new Paragraph("From Destination: " + bookingDetails.getFromLocation()));
            document.add(new Paragraph("To Destination: " + bookingDetails.getToDestination()));
            document.add(new Paragraph("Passenger Name: " + bookingDetails.getFirstName() + " " + bookingDetails.getLastName()));
            document.add(new Paragraph("Email: " + bookingDetails.getEmail()));
            document.add(new Paragraph("Mobile: " + bookingDetails.getMobile()));
            document.add(new Paragraph("Price: " + bookingDetails.getPrice()));
            document.add(new Paragraph("Bus ID: " + bookingDetails.getBusId()));
            document.add(new Paragraph("Ticket ID: " + bookingDetails.getTicketId()));

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return outputStream.toByteArray();
    }
}
