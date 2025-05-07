//package com.example.demo.repository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import com.example.demo.model.HotelsModel;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//@Repository
//public class HotelsRepository {
//
//    @Autowired
//    private JdbcTemplate template;
//
//    // Method to get all hotels
//    public List<HotelsModel> getAllHotels() {
//        String sql = "SELECT * FROM Hotels";
//        return template.query(sql, new RowMapper<HotelsModel>() {
//            @Override
//            public HotelsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
//                HotelsModel hotel = new HotelsModel();
//                hotel.setHotel_id(rs.getInt("hotel_id"));
//                hotel.setOwner_id(rs.getInt("owner_id"));
//                hotel.setName(rs.getString("name"));
//                hotel.setLocation(rs.getString("location"));
//                hotel.setCategory(rs.getString("category"));
//                hotel.setCreated_at(rs.getTimestamp("created_at"));
//                return hotel;
//            }
//        });
//    }
//
//    // Method to add a new hotel
//    public Boolean addHotel(HotelsModel model) {
//        String sql = "INSERT INTO Hotels (owner_id, name, location, category) VALUES (?, ?, ?, ?)";
//        int rowsAffected = template.update(sql, 
//            model.getOwner_id(), 
//            model.getName(),
//            model.getLocation(),
//            model.getCategory()
//        );
//        return rowsAffected > 0;
//    }
//
//    // Method to update hotel details
//    public Boolean updateHotel(HotelsModel model) {
//        if (!isValidOwner(model.getOwner_id())) {
//            System.out.println("Invalid owner ID: " + model.getOwner_id());
//            return false;
//        }
//        String sql = "UPDATE Hotels SET name = ?, location = ?, category = ?, owner_id = ? WHERE hotel_id = ?";
//        int rowsAffected = template.update(sql, 
//            model.getName(),
//            model.getLocation(),
//            model.getCategory(),
//            model.getOwner_id(),
//            model.getHotel_id()
//        );
//        return rowsAffected > 0;
//    }
//
//    // Method to delete a hotel by hotel_id
//    public Boolean deleteHotelById(int hotelId) {
//        String sql = "DELETE FROM Hotels WHERE hotel_id = ?";
//        int rowsAffected = template.update(sql, hotelId);
//        return rowsAffected > 0;
//    }
//
//    // Method to get a hotel by its ID
//    public HotelsModel getHotelById(int hotelId) {
//        String sql = "SELECT * FROM Hotels WHERE hotel_id = ?";
//        try {
//            return template.queryForObject(sql, new Object[]{hotelId}, new RowMapper<HotelsModel>() {
//                @Override
//                public HotelsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    HotelsModel hotel = new HotelsModel();
//                    hotel.setHotel_id(rs.getInt("hotel_id"));
//                    hotel.setOwner_id(rs.getInt("owner_id"));
//                    hotel.setName(rs.getString("name"));
//                    hotel.setLocation(rs.getString("location"));
//                    hotel.setCategory(rs.getString("category"));
//                    hotel.setCreated_at(rs.getTimestamp("created_at"));
//                    return hotel;
//                }
//            });
//        } catch (Exception e) {
//            return null; // Return null if not found
//        }
//    }
//
//    // Method to check if the owner_id is valid (user must have 'Owner' role)
//    public boolean isValidOwner(int ownerId) {
//        String checkOwnerSql = 
//            "SELECT COUNT(*) FROM Users u " +
//            "JOIN Roles r ON u.role_id = r.role_id " +
//            "WHERE u.user_id = ? AND r.role_name = 'Owner'";
//
//        Integer count = template.queryForObject(checkOwnerSql, new Object[]{ownerId}, Integer.class);
//        return count != null && count > 0;
//    }
//    
//    public List<HotelsModel> getHotelsByOwnerId(int ownerId) {
//        String sql = "SELECT * FROM Hotels WHERE owner_id = ?";
//        return template.query(sql, new RowMapper<HotelsModel>() {
//            @Override
//            public HotelsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
//                HotelsModel hotel = new HotelsModel();
//                hotel.setHotel_id(rs.getInt("hotel_id"));
//                hotel.setOwner_id(rs.getInt("owner_id"));
//                hotel.setName(rs.getString("name"));
//                hotel.setLocation(rs.getString("location"));
//                hotel.setCategory(rs.getString("category"));
//                hotel.setCreated_at(rs.getTimestamp("created_at"));
//                return hotel;
//            }
//        }, ownerId);
//    }
//    
//}

package com.example.demo.repository;

import com.example.demo.model.HotelsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HotelsRepository {

    @Autowired
    private JdbcTemplate template;

    // Get all hotels
    public List<HotelsModel> getAllHotels() {
        String sql = "SELECT * FROM Hotels";
        return template.query(sql, new BeanPropertyRowMapper<>(HotelsModel.class));
    }

    // Add a new hotel
    public Boolean addHotel(HotelsModel model) {
        String sql = "INSERT INTO Hotels (owner_id, name, location, category, image_url) VALUES (?, ?, ?, ?, ?)";
        int rowsAffected = template.update(sql,
                model.getOwner_id(),
                model.getName(),
                model.getLocation(),
                model.getCategory(),
                model.getImage_url()
        );
        return rowsAffected > 0;
    }

    // Update hotel
    public Boolean updateHotel(HotelsModel model) {
        if (!isValidOwner(model.getOwner_id())) {
            System.out.println("Invalid owner ID: " + model.getOwner_id());
            return false;
        }
        String sql = "UPDATE Hotels SET name = ?, location = ?, category = ?, owner_id = ?, image_url = ? WHERE hotel_id = ?";
        int rowsAffected = template.update(sql,
                model.getName(),
                model.getLocation(),
                model.getCategory(),
                model.getOwner_id(),
                model.getImage_url(),
                model.getHotel_id()
        );
        return rowsAffected > 0;
    }

    // Delete hotel
    public Boolean deleteHotelById(int hotelId) {
        String sql = "DELETE FROM Hotels WHERE hotel_id = ?";
        int rowsAffected = template.update(sql, hotelId);
        return rowsAffected > 0;
    }

    // Get hotel by ID
    public HotelsModel getHotelById(int hotelId) {
        String sql = "SELECT * FROM Hotels WHERE hotel_id = ?";
        try {
            return template.queryForObject(sql, new Object[]{hotelId}, new BeanPropertyRowMapper<>(HotelsModel.class));
        } catch (Exception e) {
            return null;
        }
    }

    // Get hotels by owner ID
    public List<HotelsModel> getHotelsByOwnerId(int ownerId) {
        String sql = "SELECT * FROM Hotels WHERE owner_id = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(HotelsModel.class), ownerId);
    }

    // Check if owner is valid (must have 'Owner' role)
    public boolean isValidOwner(int ownerId) {
        String checkOwnerSql = "SELECT COUNT(*) FROM Users u JOIN Roles r ON u.role_id = r.role_id WHERE u.user_id = ? AND r.role_name = 'Owner'";
        Integer count = template.queryForObject(checkOwnerSql, new Object[]{ownerId}, Integer.class);
        return count != null && count > 0;
    }
}

