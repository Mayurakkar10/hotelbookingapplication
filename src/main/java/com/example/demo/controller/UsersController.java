package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.UsersModel;
import com.example.demo.services.UsersService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    @Autowired
    UsersService service;

    @PostMapping("/addUsers")
    public String register(@RequestBody UsersModel user) {
        boolean added = service.isAddNewUser(user);
        return added ? "User Registered Successfully" : "Registration Failed";
    }

    @GetMapping("/getallusers")
    public List<UsersModel> getAllUsers(){
    	return service.getAllUsers();
    }
//    @PostMapping("/loginUser")
//    public String login(@RequestBody UsersModel user) {
//        String role = service.login(user);
//        return role != null ? role : "Login Failed Invalid Credentials";
//    }
    @PostMapping("/loginUser")
    public Map<String, Object> login(@RequestBody UsersModel user) {
        Map<String, Object> response = new HashMap<>();

        UsersModel loggedInUser = service.findUserByEmailAndPassword(
            user.getEmail(), user.getPassword()
        );

        if (loggedInUser != null) {
            // get the role_name string, not the numeric id
            String roleName = service.getRoleName(loggedInUser.getRole_id());
            response.put("role", roleName);
            response.put("userId", loggedInUser.getUser_id());
        } else {
            response.put("role", "login failed invalid credentials");
        }

        return response;
    }


}
