package com.example.demo.services;

import com.example.demo.model.HotelsModel;
import com.example.demo.repository.HotelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelsService {

    @Autowired
    private HotelsRepository hotelsRepository;

    // Method to get all hotels
    public List<HotelsModel> getAllHotels() {
        return hotelsRepository.getAllHotels();
    }

    public List<HotelsModel> getHotelsByOwnerId(int ownerId){
    	return hotelsRepository.getHotelsByOwnerId(ownerId);
    }
    // Method to add a new hotel
    public String addHotel(HotelsModel hotel) {
        boolean added = hotelsRepository.addHotel(hotel);
        return added ? "Hotel added successfully" : "Failed to add hotel";
    }

    // Method to update a hotel and return the updated list of hotels
    public List<HotelsModel> updateHotel(int hotelId, HotelsModel hotel) {
        hotel.setHotel_id(hotelId); // Set the hotel_id to the URL parameter
        boolean updated = hotelsRepository.updateHotel(hotel);
        return updated ? hotelsRepository.getAllHotels() : null;
    }

    // Method to delete a hotel by hotel_id
    public String deleteHotel(int hotelId) {
        boolean deleted = hotelsRepository.deleteHotelById(hotelId);
        return deleted ? "Hotel deleted successfully" : "Failed to delete hotel";
    }

    // Method to get a hotel by its ID
    public HotelsModel getHotelById(int hotelId) {
        return hotelsRepository.getHotelById(hotelId);
    }
}
