package com.luv2code.pbl4.jobseekerapplication.service;


import com.luv2code.pbl4.jobseekerapplication.dao.UserRepository;
import com.luv2code.pbl4.jobseekerapplication.entity.Roles;
import com.luv2code.pbl4.jobseekerapplication.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> result = userRepository.findById(id);

        User theUser = null;

        if(result.isPresent()) {
            theUser = result.get();
        }else{
            throw new RuntimeException("user not found");
        }
        return theUser;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> result = userRepository.findUserByEmail(email);

        User theUser = null;

        if(result.isPresent()) {
            theUser = result.get();
        }
        return theUser;
    }

    @Override
    public User save(User theUser) {

        theUser.setPassword(  passwordEncoder.encode(theUser.getPassword()));
        Roles role = new Roles();
        role.setRole("ROLE_USER");
        role.setUser(theUser);
        List<Roles> roles = new ArrayList<>();
        roles.add(role);
        theUser.setRoles(roles);
        return userRepository.save(theUser);
    }

    @Override
    public void update(User theUser) {
        userRepository.updateUser(theUser.getUserId(), theUser.getUsername(), theUser.getEmail(), theUser.getPassword());
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> getUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> searchUsers(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return userRepository.searchUsers(keyword, pageable);
    }


}
