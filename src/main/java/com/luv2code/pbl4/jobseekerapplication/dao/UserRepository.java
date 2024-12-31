package com.luv2code.pbl4.jobseekerapplication.dao;


import com.luv2code.pbl4.jobseekerapplication.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:keyword% OR u.email LIKE %:keyword%")
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.username = :username, u.email = :email, u.password = :password WHERE u.userId = :userId")
    void updateUser(@Param("userId") Integer userId, @Param("username") String username, @Param("email") String email, @Param("password") String password);



}
