package com.example.demo.repository;

import com.example.demo.model.BookingsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
@Repository
public class BookingsRepository {

    @Autowired
    private JdbcTemplate template;

    public List<BookingsModel> getAllBookings() {
        String sql = "SELECT * FROM Bookings";
        return template.query(sql, new RowMapper<>() {
            @Override
            public BookingsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                BookingsModel booking = new BookingsModel();
                booking.setBooking_id(rs.getInt("booking_id"));
                booking.setCustomer_id(rs.getInt("customer_id"));
                booking.setHotel_id(rs.getInt("hotel_id"));
                booking.setRoom_id(rs.getInt("room_id"));
                booking.setCheck_in_date(rs.getDate("check_in_date"));
                booking.setCheck_out_date(rs.getDate("check_out_date"));
                booking.setTotal_price(rs.getBigDecimal("total_price"));
                booking.setStatus(rs.getString("status"));
                booking.setReview_rating(rs.getInt("review_rating"));
                booking.setReview_text(rs.getString("review_text"));
                booking.setCreated_at(rs.getTimestamp("created_at"));
                return booking;
            }
        });
    }

    public Integer addBookingReturnId(BookingsModel booking) {
        if (!isValidCustomer(booking.getCustomer_id())) return null;
        if (!isValidHotel(booking.getHotel_id())) return null;
        if (!isRoomValidForHotel(booking.getRoom_id(), booking.getHotel_id())) return null;

        String sql = "INSERT INTO Bookings (customer_id, hotel_id, room_id, check_in_date, check_out_date, total_price, status, review_rating, review_text, number_of_guests, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, booking.getCustomer_id());
            ps.setInt(2, booking.getHotel_id());
            ps.setInt(3, booking.getRoom_id());
            ps.setDate(4, new java.sql.Date(booking.getCheck_in_date().getTime()));
            ps.setDate(5, new java.sql.Date(booking.getCheck_out_date().getTime()));
            ps.setBigDecimal(6, booking.getTotal_price());
            ps.setString(7, booking.getStatus());
            ps.setObject(8, booking.getReview_rating(), java.sql.Types.INTEGER);
            ps.setString(9, booking.getReview_text());
            ps.setInt(10, booking.getNumber_of_guests());
            ps.setTimestamp(11, new java.sql.Timestamp(booking.getCreated_at().getTime()));
            return ps;
        }, keyHolder);

        return rowsAffected > 0 ? keyHolder.getKey().intValue() : null;
    }
    public Boolean addBooking(BookingsModel booking) {
        if (!isValidCustomer(booking.getCustomer_id())) return false;
        if (!isValidHotel(booking.getHotel_id())) return false;
        if (!isRoomValidForHotel(booking.getRoom_id(), booking.getHotel_id())) return false;

        String sql = "INSERT INTO Bookings (customer_id, hotel_id, room_id, check_in_date, check_out_date, total_price, status, review_rating, review_text, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rowsAffected = template.update(sql,
                booking.getCustomer_id(),
                booking.getHotel_id(),
                booking.getRoom_id(),
                booking.getCheck_in_date(),
                booking.getCheck_out_date(),
                booking.getTotal_price(),
                booking.getStatus(),
                booking.getReview_rating(),
                booking.getReview_text(),
                booking.getCreated_at());
        return rowsAffected > 0;
    }

    private boolean isValidCustomer(int customerId) {
        String sql = "SELECT COUNT(*) FROM Users WHERE user_id = ? AND role_id = (SELECT role_id FROM Roles WHERE role_name = 'Customer')";
        Integer count = template.queryForObject(sql, Integer.class, customerId);
        return count != null && count > 0;
    }

    private boolean isValidHotel(int hotelId) {
        String sql = "SELECT COUNT(*) FROM Hotels WHERE hotel_id = ?";
        Integer count = template.queryForObject(sql, Integer.class, hotelId);
        return count != null && count > 0;
    }

    private boolean isRoomValidForHotel(int roomId, int hotelId) {
        String sql = "SELECT COUNT(*) FROM Rooms WHERE room_id = ? AND hotel_id = ?";
        Integer count = template.queryForObject(sql, Integer.class, roomId, hotelId);
        return count != null && count > 0;
    }

    public Map<String, Object> getBookingById(int bookingId) {
        String sql = """
            SELECT b.*, h.name AS hotel_name, rt.type_name AS room_type
            FROM Bookings b
            JOIN Hotels h ON b.hotel_id = h.hotel_id
            JOIN Rooms r ON b.room_id = r.room_id
            JOIN Room_Types rt ON r.type_id = rt.type_id
            WHERE b.booking_id = ?
        """;
        return template.queryForMap(sql, bookingId);
    }

    public List<Map<String, Object>> getBookingsByOwner(int ownerId) {
        String sql = """
            SELECT b.booking_id, b.check_in_date, b.check_out_date, b.total_price,
                   b.status AS booking_status, b.review_rating, b.review_text,
                   h.name AS hotel_name, rt.type_name AS room_type, u.name AS customer_name
            FROM bookings b
            JOIN hotels h ON b.hotel_id = h.hotel_id
            JOIN rooms r ON b.room_id = r.room_id
            JOIN room_types rt ON r.type_id = rt.type_id
            JOIN users u ON b.customer_id = u.user_id
            WHERE h.owner_id = ?
        """;
        return template.queryForList(sql, ownerId);
    }

    public List<Map<String, Object>> getBookingsByCustomer(int customerId) {
        String sql = """
            SELECT b.booking_id, b.check_in_date, b.check_out_date, b.total_price,
                   b.status, b.review_rating, b.review_text,
                   h.name AS hotel_name, rt.type_name AS room_type
            FROM Bookings b
            JOIN Hotels h ON b.hotel_id = h.hotel_id
            JOIN Rooms r ON b.room_id = r.room_id
            JOIN Room_Types rt ON r.type_id = rt.type_id
            WHERE b.customer_id = ?
        """;
        return template.queryForList(sql, customerId);
    }

    public int addReview(int bookingId, int rating, String review) {
        String sql = "UPDATE Bookings SET review_rating = ?, review_text = ? WHERE booking_id = ?";
        return template.update(sql, rating, review, bookingId);
    }
    
    
    
    public List<Map<String, Object>> getHotelDetails() {
        String sql = "SELECT h.hotel_id, h.name, h.location, h.category, h.image_url, " +
                     "COUNT(DISTINCT r.room_id) AS available_rooms, " +
                     "AVG(DISTINCT r.price) AS avg_price, " +
                     "AVG(b.review_rating) AS avg_rating, " +
                     "SUM(CASE WHEN ra.amenity_name = 'AC' THEN 1 ELSE 0 END) AS ac_rooms, " +
                     "SUM(CASE WHEN ra.amenity_name = 'Non-AC' THEN 1 ELSE 0 END) AS non_ac_rooms " +
                     "FROM Hotels h " +
                     "LEFT JOIN Rooms r ON h.hotel_id = r.hotel_id AND r.availability_status = 'Available' " +
                     "LEFT JOIN Room_Features rf ON r.room_id = rf.room_id " +
                     "LEFT JOIN Room_Amenities ra ON rf.amenity_id = ra.amenity_id " +
                     "LEFT JOIN Bookings b ON h.hotel_id = b.hotel_id " +
                     "GROUP BY h.hotel_id, h.name, h.location, h.category";

        return template.queryForList(sql);
    }


    

}
