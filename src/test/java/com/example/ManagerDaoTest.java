package com.example;

import com.example.dao.IManagerDao;
import com.example.entity.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ManagerDaoTest {
    //managerDao=null解决：
    //原因是没有加上@SpringBootTest，导致ImanagerDao没有注入到IOC容器中，自然，不能通过@Autowired自动装配得到实例了
    @Autowired
    private IManagerDao managerDao;
    @Test
    public void findManager(){
        managerDao.findManagerByIdAndPassword(1,"123456");
        managerDao.findManagerByName("admin");
    }
    @Test
    public void save(){
        Manager manager=new Manager();
        manager.setName("root2");
        manager.setPassword("852741");
        managerDao.saveAndFlush(manager);
    }
    @Test
    public void delete(){
//        No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
        //原因：没有开启事务，所以不能执行,在对应抽象方法上加@Tranctional
        managerDao.deleteByIdAndPassword(1,"123456");
    }
    @Test
    public void update(){
        managerDao.updateManagerNameAndPasswordById("123456","superadmin1",2);
    }

    @Test
    public void deletebyid(){
        managerDao.deleteById(6);
    }
}
