package com.luv2code.pbl4.jobseekerapplication.service;

import com.luv2code.pbl4.jobseekerapplication.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

    User save(User theUser);

    void update(User theUser);

    void deleteById(int id);

    public Page<User> getUsers(int pageNo, int pageSize) ;

    public Page<User> searchUsers(String keyword, int pageNo, int pageSize);
}
