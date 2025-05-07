package com.example.demo.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class GuestDetailsModel {

	    private int guest_id;
	    private int booking_id;
	    private String name;
	    private int age;
	    private int gender;  // Gender stored as an integer
	    private String id_proof_type;
	    private String id_proof_number;
	    private Timestamp created_at;

}

