package com.example.demo.controller;

import com.example.demo.model.GuestDetailsModel;
import com.example.demo.services.GuestDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
public class GuestDetailsController {

    @Autowired
    private GuestDetailsService guestDetailsService;

    // Get all guests for a specific booking
    @GetMapping("/guests/{bookingId}")
    public List<GuestDetailsModel> getGuestsByBookingId(@PathVariable int bookingId) {
        return guestDetailsService.getGuestsByBookingId(bookingId);
    }

    // Add a new guest to a booking
    @PostMapping("/booking/{bookId}/addGuest")
    public boolean addGuestDetailsForBooking(@PathVariable int bookId, @RequestBody GuestDetailsModel guest) {
        return guestDetailsService.addGuestDetailsForBooking(bookId, guest);
    }

    // Update guest details
    @PutMapping("/guest/update")
    public boolean updateGuestDetails(@RequestBody GuestDetailsModel guest) {
        return guestDetailsService.updateGuestDetails(guest);
    }

    // Delete guest details by guest ID
    @DeleteMapping("/guest/{guestId}")
    public boolean deleteGuestDetails(@PathVariable int guestId) {
        return guestDetailsService.deleteGuestDetailsById(guestId);
    }
}
