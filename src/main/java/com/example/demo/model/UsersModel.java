package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UsersModel {
	private int user_id;
	private String name;
	private String email;
	private String password;
	private String phone;
	private int role_id;
	private Timestamp created_at;
}
