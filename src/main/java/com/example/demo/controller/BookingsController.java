package com.example.demo.controller;

import com.example.demo.model.BookingsModel;
import com.example.demo.services.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class BookingsController {

    @Autowired
    private BookingsService bookingsService;
    
    @PostMapping("/addBooking")
    public ResponseEntity<?> addBooking(@RequestBody BookingsModel booking) {
        Integer bookingId = bookingsService.addBooking(booking);

        if (bookingId == null) {
            return ResponseEntity.badRequest().body("Booking validation failed or could not insert.");
        }

        Map<String, Object> response = Map.of("bookingId", bookingId);
        return ResponseEntity.ok(response);
    }



    @GetMapping("/getAllBookings")
    public List<BookingsModel> getAllBookings() {
        return bookingsService.getAllBookings();
    }
    
    @GetMapping("/hotels")
    public List<Map<String, Object>> getHotelDetails() {
        return bookingsService.getHotelDetails();
    }

    @GetMapping("/searchBookingById/{bookingId}")
    public Map<String, Object> getBookingById(@PathVariable int bookingId) {
        return bookingsService.getBookingById(bookingId);
    }

    @GetMapping("bookings/owner/{ownerId}")
    public List<Map<String, Object>> getBookingsByOwner(@PathVariable int ownerId) {
        return bookingsService.getBookingsByOwner(ownerId);
    }

    @GetMapping("bookings/customer/{customerId}")
    public List<Map<String, Object>> getBookingsByCustomer(@PathVariable int customerId) {
        return bookingsService.getBookingsByCustomer(customerId);
    }

    @PostMapping("/{bookingId}/review")
    public ResponseEntity<Map<String, Object>> addReview(
        @PathVariable int bookingId,
        @RequestBody Map<String, Object> payload
    ) {
        int rating = (int) payload.get("review_rating");
        String review = (String) payload.get("review_text");

        Map<String, Object> updated = bookingsService.addReview(bookingId, rating, review);
        return ResponseEntity.ok(updated);
    }
}

