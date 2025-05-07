package com.example.demo.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class HotelsModel {
    private int hotel_id;
    private int owner_id;   
    private String name;
    private String location;
    private String category;
    private String image_url;
    private Timestamp created_at;  
}
