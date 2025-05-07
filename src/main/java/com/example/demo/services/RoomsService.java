package com.example.demo.services;

import com.example.demo.model.RoomsModel;
import com.example.demo.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {

    @Autowired
    private RoomsRepository roomsRepository;

    public List<RoomsModel> getAllRooms() {
        return roomsRepository.getAllRooms();
    }

    public RoomsModel getRoomById(int roomId) {
        return roomsRepository.getRoomById(roomId);
    }

    public List<RoomsModel> getRoomsByHotelId(int hotelId) {
        return roomsRepository.getRoomsByHotelId(hotelId);
    }

    public boolean addRoom(RoomsModel room) {
        return roomsRepository.addRoom(room);
    }

    public boolean updateRoom(RoomsModel room) {
        return roomsRepository.updateRoom(room);
    }

    public boolean updateRoomAvailability(int roomId, String status) {
        return roomsRepository.updateRoomAvailability(roomId, status);
    }

    public boolean deleteRoomById(int roomId) {
        return roomsRepository.deleteRoomById(roomId);
    }
}
