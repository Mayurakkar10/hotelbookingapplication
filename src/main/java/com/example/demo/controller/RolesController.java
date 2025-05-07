package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ValueNotFoundException;
import com.example.demo.model.RolesModel;
import com.example.demo.services.RolesService;

@RestController
@CrossOrigin
public class RolesController {
	
	@Autowired
	RolesService service;
	
	@GetMapping("/viewAllRoles")	
	public List<RolesModel> getAllRoles(){
	    List <RolesModel>list = service.getAllRoles();
	    if(list.size()!=0) {
	    	return list;
	    }
	    else {
	    	throw new ValueNotFoundException("There is no value in table");
	    }
	   }
}
