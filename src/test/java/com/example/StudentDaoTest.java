package com.example;

import com.example.dao.IStudentDao;
import com.example.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentDaoTest {
    @Autowired
    IStudentDao studentDao;
    @Test
    public void findStudentById(){
        studentDao.findStudentById(1);
    }
    @Test
    public void findStudentByName(){
        studentDao.findStudentByName("李云龙");
    }
    @Test
    public void update(){
        studentDao.updateStudentNameAndPasswordById("123456","赵政委",16);
    }
    @Test
    public void save(){
        Student student=new Student();
        student.setName("Dior");
        student.setPassword("123456");
        studentDao.saveAndFlush(student);
    }
    @Test
    public void delete(){
        studentDao.deleteByIdAndPassword(7,"11121444");
    }
}
