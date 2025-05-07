package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class BookingsModel {

    private int booking_id;
    private int customer_id;
    private int hotel_id;
    private int room_id;
    private Date check_in_date;
    private Date check_out_date;
    private BigDecimal total_price;
    private String status;
    private Integer review_rating;
    private String review_text;
    private Timestamp created_at;
    private int number_of_guests; // Number of guests for the booking
    private List<GuestDetailsModel> guest_details; // List of guest details

}
