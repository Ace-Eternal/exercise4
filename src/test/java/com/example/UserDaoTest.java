package com.example;

import com.example.dao.IUserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    IUserDao userDao;
    @Test
    public void findById(){
        userDao.findById(1);
    }
}
