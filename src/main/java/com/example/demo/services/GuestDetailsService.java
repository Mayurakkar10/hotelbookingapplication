package com.example.demo.services;

import com.example.demo.model.GuestDetailsModel;
import com.example.demo.repository.GuestDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestDetailsService {

    @Autowired
    private GuestDetailsRepository guestDetailsRepository;

    // Get all guests for a specific booking
    public List<GuestDetailsModel> getGuestsByBookingId(int bookingId) {
        return guestDetailsRepository.getGuestsByBookingId(bookingId);
    }

    // Add guest details to a booking
    public boolean addGuestDetailsForBooking(int bookId, GuestDetailsModel guest) {
        // Set the booking ID on the guest model before saving
        guest.setBooking_id(bookId);

        // Call the repository to add the guest details
        return guestDetailsRepository.addGuestDetails(guest);
    }

    // Update guest details
    public boolean updateGuestDetails(GuestDetailsModel guest) {
        return guestDetailsRepository.updateGuestDetails(guest);
    }

    // Delete guest details by guest ID
    public boolean deleteGuestDetailsById(int guestId) {
        return guestDetailsRepository.deleteGuestDetailsById(guestId);
    }
}
