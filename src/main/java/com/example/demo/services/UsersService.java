package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UsersModel;
import com.example.demo.repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    UsersRepository repo;

    public boolean isAddNewUser(UsersModel user) {
        return repo.isAddNewUser(user);
    }

    public List<UsersModel> getAllUsers(){
    	return repo.getAllUsers();
    }
    public String login(UsersModel user) {
        boolean isValid = repo.validateUser(user.getEmail(), user.getPassword());
        if (isValid) {
            return repo.getUserRole(user.getEmail());
        }
        return null;
    }
    public UsersModel findUserByEmailAndPassword(String email, String password) {
        return repo.findUserByEmailAndPassword(email, password);
    }

    /**
     * Given a numeric role_id, return the role_name string.
     */
    public String getRoleName(int roleId) {
        return repo.findRoleNameByRoleId(roleId);
    }
}
