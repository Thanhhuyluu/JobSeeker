package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

    User save(User theUser);

    void deleteById(int id);
}
