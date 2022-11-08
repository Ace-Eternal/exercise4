package com.example.dao;

import com.example.entity.Manager;
import com.example.entity.Student;
import io.swagger.models.auth.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

public interface IStudentDao extends JpaRepository<Student,Integer> {
//    Error creating bean with name 'IStudentDao' defined in com.example.dao.IStudentDao
    //原因:接口抽象方法有错误，比如没有写参数
    //正确的 Student findStudentByNameAndPassword(String name,String password);
     Student findStudentByNameAndPassword(String name,String password);
     Student findStudentByIdAndPassword(Integer id,String password);
     Student findStudentById(Integer id);

     Student findStudentByName(String name);

     @Transactional
     @Modifying
     @Query("update Student s set s.password=?1,s.name=?2 where s.id=?3")
     //postman报500服务器内部错误
     //原因：这里的方法中参数没有对应上
     //正确的对应s.password=?1 where s.id=?2"
     //正确的对应(String password,Integer id);
     void updateStudentNameAndPasswordById(String password,String name,Integer id);

     //添加学生
     Student saveAndFlush(Student student);
     //删除学生
     @Transactional
     Integer deleteByIdAndPassword(Integer id,String passowrd);
}
