package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class RoomsModel {

    private int room_id;
    private int hotel_id;
    private int type_id;
    private BigDecimal price;
    private String availability_status;
    private String image_url;
    @JsonProperty("amenities")
    private List<String> amenityIds;
    private Timestamp created_at;
    
    private String room_type;
    private int bed_count;         // ✅ New field
    private int max_guests;
   
}
