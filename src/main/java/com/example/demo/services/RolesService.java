package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.RolesModel;
import com.example.demo.repository.RolesRepository;

@Service
public class RolesService {
@Autowired
		RolesRepository repo;
	public List<RolesModel> getAllRoles() {
		return repo.getAllRoles();
	}

}
