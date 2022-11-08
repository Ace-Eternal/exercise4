package com.example.dao;

import com.example.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface IManagerDao extends JpaRepository<Manager,Integer> {
@Transactional
@Modifying
    void deleteById(Integer id);
    //根据id和password找用户，登录用
    Manager findManagerById(Integer id);
    Manager findManagerByIdAndPassword(Integer id,String password);
    Manager findManagerByName(String name);
    Manager findManagerByNameAndPassword(String name,String password);
    //删除管理员
    @Transactional
    Integer deleteByIdAndPassword(Integer id,String passowrd);
    //添加用户
    Manager saveAndFlush(Manager manager);
    //修改管理员密码和姓名
    @Transactional
    @Modifying
    @Query("update Manager m set m.password=?1,m.name=?2 where m.id=?3")
    void updateManagerNameAndPasswordById(String password,String name,Integer id);


}
