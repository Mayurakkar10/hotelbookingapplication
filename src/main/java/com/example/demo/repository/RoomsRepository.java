//package com.example.demo.repository;
//
//import com.example.demo.model.RoomsModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//public class RoomsRepository {
//
//    @Autowired
//    private JdbcTemplate template;
//
////    private final RowMapper<RoomsModel> roomMapper = (rs, rowNum) -> {
////        RoomsModel room = new RoomsModel();
////        room.setRoom_id(rs.getInt("room_id"));
////        room.setHotel_id(rs.getInt("hotel_id"));
////        room.setType_id(rs.getInt("type_id"));
////        room.setPrice(rs.getBigDecimal("price"));
////        room.setAvailability_status(rs.getString("availability_status"));
////        room.setImage_url(rs.getString("image_url"));
////        room.setCreated_at(rs.getTimestamp("created_at"));
////        return room;
////    };
//    private final RowMapper<RoomsModel> roomMapper = (rs, rowNum) -> {
//        RoomsModel room = new RoomsModel();
//        room.setRoom_id(rs.getInt("room_id"));
//        room.setHotel_id(rs.getInt("hotel_id"));
//        room.setType_id(rs.getInt("type_id"));
//        room.setPrice(rs.getBigDecimal("price"));
//        room.setAvailability_status(rs.getString("availability_status"));
//        room.setImage_url(rs.getString("image_url"));
//        room.setCreated_at(rs.getTimestamp("created_at"));
//        room.setBed_count(rs.getInt("bed_count"));           // ✅ added
//        room.setMax_guests(rs.getInt("max_guests"));         // ✅ added
//        return room;
//    };
//
//    public List<RoomsModel> getAllRooms() {
//        return template.query("SELECT * FROM Rooms", roomMapper);
//    }
//
//    public RoomsModel getRoomById(int roomId) {
//        try {
//            return template.queryForObject("SELECT * FROM Rooms WHERE room_id = ?", new Object[]{roomId}, roomMapper);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List<RoomsModel> getRoomsByHotelId(int hotelId) {
//        String sql = """
//            SELECT r.*, rt.type_name 
//            FROM Rooms r
//            JOIN Room_Types rt ON r.type_id = rt.type_id
//            WHERE r.hotel_id = ?
//        """;
//
//        return template.query(sql, new Object[]{hotelId}, (rs, rowNum) -> {
//            RoomsModel room = roomMapper.mapRow(rs, rowNum);
//            room.setRoom_type(rs.getString("type_name")); // Add room type name
//
//            // Fetch and set amenities
//            List<String> amenities = getAmenitiesByRoomId(room.getRoom_id());
//            room.setAmenityIds(amenities);
//
//            return room;
//        });
//    }
//
//    private List<String> getAmenitiesByRoomId(int roomId) {
//        String sql = """
//            SELECT ra.amenity_name
//            FROM Room_Features rf
//            JOIN Room_Amenities ra ON rf.amenity_id = ra.amenity_id
//            WHERE rf.room_id = ?
//        """;
//        return template.queryForList(sql, new Object[]{roomId}, String.class);
//    }
//
//
//
//    @Transactional
//    public boolean addRoom(RoomsModel room) {
//        if (!isValidHotel(room.getHotel_id()) || !isValidRoomType(room.getType_id())) {
//            return false;
//        }
//
//        String sql = "INSERT INTO Rooms (hotel_id, type_id, price, availability_status, image_url, created_at) VALUES (?, ?, ?, ?, ?, ?)";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
//        int rows = template.update(connection -> {
//            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, room.getHotel_id());
//            ps.setInt(2, room.getType_id());
//            ps.setBigDecimal(3, room.getPrice());
//            ps.setString(4, room.getAvailability_status());
//            ps.setString(5, room.getImage_url());
//            ps.setTimestamp(6, room.getCreated_at());
//            return ps;
//        }, keyHolder);
//
//        if (rows > 0 && room.getAmenityIds() != null && !room.getAmenityIds().isEmpty()) {
//            int roomId = keyHolder.getKey().intValue();
//            insertRoomAmenities(roomId, room.getAmenityIds());
//        }
//
//        return rows > 0;
//    }
//
//    
//    @Transactional
//    public boolean updateRoom(RoomsModel room) {
//        // Validate hotel and room type IDs
//        if (!isValidHotel(room.getHotel_id()) || !isValidRoomType(room.getType_id())) {
//            return false;
//        }
//
//        // Update the main room fields
//        String sql = """
//            UPDATE Rooms 
//            SET hotel_id = ?, type_id = ?, price = ?, availability_status = ?, image_url = ? 
//            WHERE room_id = ?
//        """;
//
//        int rows = template.update(sql,
//                room.getHotel_id(),
//                room.getType_id(),
//                room.getPrice(),
//                room.getAvailability_status(),
//                room.getImage_url(),
//                room.getRoom_id());
//
//        // If update was successful, handle amenities
//        if (rows > 0) {
//            // Clear existing amenities
//            template.update("DELETE FROM Room_Features WHERE room_id = ?", room.getRoom_id());
//
//            // Insert new amenities, if provided
//            if (room.getAmenityIds() != null && !room.getAmenityIds().isEmpty()) {
//                insertRoomAmenities(room.getRoom_id(), room.getAmenityIds());
//            }
//        }
//
//        return rows > 0;
//    }
//
//
//    public boolean updateRoomAvailability(int roomId, String status) {
//        return template.update("UPDATE Rooms SET availability_status = ? WHERE room_id = ?", status, roomId) > 0;
//    }
//
//    public boolean deleteRoomById(int roomId) {
//        return template.update("DELETE FROM Rooms WHERE room_id = ?", roomId) > 0;
//    }
//
//    private boolean isValidHotel(int hotelId) {
//        Integer count = template.queryForObject("SELECT COUNT(*) FROM Hotels WHERE hotel_id = ?", new Object[]{hotelId}, Integer.class);
//        return count != null && count > 0;
//    }
//
//    private boolean isValidRoomType(int typeId) {
//        Integer count = template.queryForObject("SELECT COUNT(*) FROM Room_Types WHERE type_id = ?", new Object[]{typeId}, Integer.class);
//        return count != null && count > 0;
//    }
//    
//    private void insertRoomAmenities(int roomId, List<String> amenities) {
//        // Query to fetch amenity_id based on amenity_name
//        String sql = "SELECT amenity_id FROM Room_Amenities WHERE amenity_name = ?";
//
//        // List to hold the parameters for batch update
//        List<Object[]> batchArgs = new ArrayList<>();
//
//        for (String amenity : amenities) {
//            Integer amenityId = template.queryForObject(sql, new Object[]{amenity}, Integer.class);
//            
//            if (amenityId != null) {
//                batchArgs.add(new Object[]{roomId, amenityId});
//            } else {
//                System.out.println("Amenity not found: " + amenity);
//            }
//        }
//
//        // Batch insert into Room_Features
//        if (!batchArgs.isEmpty()) {
//            String insertSql = "INSERT INTO Room_Features (room_id, amenity_id) VALUES (?, ?)";
//            template.batchUpdate(insertSql, batchArgs);
//        }
//    }
//}
package com.example.demo.repository;

import com.example.demo.model.RoomsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoomsRepository {

    @Autowired
    private JdbcTemplate template;

    private final RowMapper<RoomsModel> roomMapper = (rs, rowNum) -> {
        RoomsModel room = new RoomsModel();
        room.setRoom_id(rs.getInt("room_id"));
        room.setHotel_id(rs.getInt("hotel_id"));
        room.setType_id(rs.getInt("type_id"));
        room.setPrice(rs.getBigDecimal("price"));
        room.setAvailability_status(rs.getString("availability_status"));
        room.setImage_url(rs.getString("image_url"));
        room.setCreated_at(rs.getTimestamp("created_at"));
        room.setBed_count(rs.getInt("bed_count"));             // New field
        room.setMax_guests(rs.getInt("max_guests"));           // New field
        return room;
    };

    public List<RoomsModel> getAllRooms() {
        return template.query("SELECT * FROM Rooms", roomMapper);
    }

    public RoomsModel getRoomById(int roomId) {
        try {
            return template.queryForObject("SELECT * FROM Rooms WHERE room_id = ?", new Object[]{roomId}, roomMapper);
        } catch (Exception e) {
            return null;
        }
    }

    public List<RoomsModel> getRoomsByHotelId(int hotelId) {
        String sql = """
            SELECT r.*, rt.type_name 
            FROM Rooms r
            JOIN Room_Types rt ON r.type_id = rt.type_id
            WHERE r.hotel_id = ?
        """;

        return template.query(sql, new Object[]{hotelId}, (rs, rowNum) -> {
            RoomsModel room = roomMapper.mapRow(rs, rowNum);
            room.setRoom_type(rs.getString("type_name"));

            // Fetch amenities
            List<String> amenities = getAmenitiesByRoomId(room.getRoom_id());
            room.setAmenityIds(amenities);

            return room;
        });
    }

    private List<String> getAmenitiesByRoomId(int roomId) {
        String sql = """
            SELECT ra.amenity_name
            FROM Room_Features rf
            JOIN Room_Amenities ra ON rf.amenity_id = ra.amenity_id
            WHERE rf.room_id = ?
        """;
        return template.queryForList(sql, new Object[]{roomId}, String.class);
    }

    @Transactional
    public boolean addRoom(RoomsModel room) {
        if (!isValidHotel(room.getHotel_id()) || !isValidRoomType(room.getType_id())) {
            return false;
        }

        String sql = """
            INSERT INTO Rooms (hotel_id, type_id, price, availability_status, image_url, bed_count, max_guests, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, room.getHotel_id());
            ps.setInt(2, room.getType_id());
            ps.setBigDecimal(3, room.getPrice());
            ps.setString(4, room.getAvailability_status());
            ps.setString(5, room.getImage_url());
            ps.setInt(6, room.getBed_count());        // New
            ps.setInt(7, room.getMax_guests());       // New
            ps.setTimestamp(8, room.getCreated_at());
            return ps;
        }, keyHolder);

        if (rows > 0 && room.getAmenityIds() != null && !room.getAmenityIds().isEmpty()) {
            int roomId = keyHolder.getKey().intValue();
            insertRoomAmenities(roomId, room.getAmenityIds());
        }

        return rows > 0;
    }

    @Transactional
    public boolean updateRoom(RoomsModel room) {
        if (!isValidHotel(room.getHotel_id()) || !isValidRoomType(room.getType_id())) {
            return false;
        }

        String sql = """
            UPDATE Rooms 
            SET hotel_id = ?, type_id = ?, price = ?, availability_status = ?, image_url = ?, bed_count = ?, max_guests = ?
            WHERE room_id = ?
        """;

        int rows = template.update(sql,
                room.getHotel_id(),
                room.getType_id(),
                room.getPrice(),
                room.getAvailability_status(),
                room.getImage_url(),
                room.getBed_count(),         // New
                room.getMax_guests(),        // New
                room.getRoom_id());

        if (rows > 0) {
            template.update("DELETE FROM Room_Features WHERE room_id = ?", room.getRoom_id());
            if (room.getAmenityIds() != null && !room.getAmenityIds().isEmpty()) {
                insertRoomAmenities(room.getRoom_id(), room.getAmenityIds());
            }
        }

        return rows > 0;
    }

    public boolean updateRoomAvailability(int roomId, String status) {
        return template.update("UPDATE Rooms SET availability_status = ? WHERE room_id = ?", status, roomId) > 0;
    }

    public boolean deleteRoomById(int roomId) {
        return template.update("DELETE FROM Rooms WHERE room_id = ?", roomId) > 0;
    }

    private boolean isValidHotel(int hotelId) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM Hotels WHERE hotel_id = ?", new Object[]{hotelId}, Integer.class);
        return count != null && count > 0;
    }

    private boolean isValidRoomType(int typeId) {
        Integer count = template.queryForObject("SELECT COUNT(*) FROM Room_Types WHERE type_id = ?", new Object[]{typeId}, Integer.class);
        return count != null && count > 0;
    }

    private void insertRoomAmenities(int roomId, List<String> amenities) {
        String sql = "SELECT amenity_id FROM Room_Amenities WHERE amenity_name = ?";
        List<Object[]> batchArgs = new ArrayList<>();

        for (String amenity : amenities) {
            Integer amenityId = template.queryForObject(sql, new Object[]{amenity}, Integer.class);
            if (amenityId != null) {
                batchArgs.add(new Object[]{roomId, amenityId});
            } else {
                System.out.println("Amenity not found: " + amenity);
            }
        }

        if (!batchArgs.isEmpty()) {
            String insertSql = "INSERT INTO Room_Features (room_id, amenity_id) VALUES (?, ?)";
            template.batchUpdate(insertSql, batchArgs);
        }
    }
}

