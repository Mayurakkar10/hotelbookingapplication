package com.example.demo.repository;

import com.example.demo.model.GuestDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GuestDetailsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map ResultSet to GuestDetailsModel
    private final RowMapper<GuestDetailsModel> guestRowMapper = (rs, rowNum) -> {
        GuestDetailsModel guest = new GuestDetailsModel();
        guest.setGuest_id(rs.getInt("guest_id"));
        guest.setBooking_id(rs.getInt("booking_id"));
        guest.setName(rs.getString("name"));
        guest.setAge(rs.getInt("age"));
        guest.setGender(rs.getInt("gender"));  // Mapping gender as integer
        guest.setId_proof_type(rs.getString("id_proof_type"));
        guest.setId_proof_number(rs.getString("id_proof_number"));
        guest.setCreated_at(rs.getTimestamp("created_at"));
        return guest;
    };

    // Method to fetch guest details by booking ID
    public List<GuestDetailsModel> getGuestsByBookingId(int bookingId) {
        String sql = "SELECT * FROM Guests WHERE booking_id = ?";
        return jdbcTemplate.query(sql, new Object[]{bookingId}, guestRowMapper);
    }

    // Method to add guest details to a booking
    public boolean addGuestDetails(GuestDetailsModel guest) {
        String sql = "INSERT INTO Guests (booking_id, name, age, gender, id_proof_type, id_proof_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        int rowsAffected = jdbcTemplate.update(sql, guest.getBooking_id(),guest.getName(),guest.getAge(),guest.getGender(),guest.getId_proof_type(),guest.getId_proof_number());

        return rowsAffected > 0;
    }

    // Method to delete guest details by guest ID
    public boolean deleteGuestDetailsById(int guestId) {
        String sql = "DELETE FROM Guests WHERE guest_id = ?";
        return jdbcTemplate.update(sql, guestId) > 0;
    }

    // Method to update guest details
    public boolean updateGuestDetails(GuestDetailsModel guest) {
        String sql = "UPDATE Guests SET name = ?, age = ?, gender = ?, id_proof_type = ?, id_proof_number = ? " +
                     "WHERE guest_id = ?";
        return jdbcTemplate.update(sql, guest.getName(), guest.getAge(), guest.getGender(),
                                   guest.getId_proof_type(), guest.getId_proof_number(), guest.getGuest_id()) > 0;
    }
}
