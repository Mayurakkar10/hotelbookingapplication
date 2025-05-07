package com.example.demo.services;

import com.example.demo.model.BookingsModel;
import com.example.demo.repository.BookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookingsService {

    @Autowired
    private BookingsRepository bookingsRepository;

    public List<BookingsModel> getAllBookings() {
        return bookingsRepository.getAllBookings();
    }
    public Integer addBooking(BookingsModel booking) {
        return bookingsRepository.addBookingReturnId(booking);
    }
    public List<Map<String, Object>> getBookingsByOwner(int ownerId) {
        return bookingsRepository.getBookingsByOwner(ownerId);
    }

    public List<Map<String, Object>> getBookingsByCustomer(int customerId) {
        return bookingsRepository.getBookingsByCustomer(customerId);
    }

    public Map<String, Object> getBookingById(int bookingId) {
        return bookingsRepository.getBookingById(bookingId);
    }

    public Map<String, Object> addReview(int bookingId, int rating, String review) {
        bookingsRepository.addReview(bookingId, rating, review);
        return bookingsRepository.getBookingById(bookingId);
    }
    public List<Map<String, Object>> getHotelDetails() {
        return bookingsRepository.getHotelDetails();
    }
    
}

