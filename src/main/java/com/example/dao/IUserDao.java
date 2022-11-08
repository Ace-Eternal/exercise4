package com.example.dao;

import com.example.entity.User;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Integer> {
    public User findUserById(Integer id);
    public User findFirstById(Integer id);
}
