package com.example.service;

import com.example.dao.IManagerDao;
import com.example.entity.Manager;
import com.example.entity.Student;
import com.example.util.JWTUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
//完成跨域
@CrossOrigin
public class ManagerService {
    @Autowired
    private IManagerDao managerDao;
    @ApiOperation("管理员登录")
    @ApiImplicitParam(name = "manager",value = "管理员实体",required = true)
    //post请求，其参数放在请求体body里面
    @PostMapping("/login")
    //一旦加上@RequestParam("")就意味着这个参数必须有，不然这个方法不会被访问到(400),除非加上required=false
    public Manager login(@RequestBody Manager manager){
        Manager m=managerDao.findManagerByIdAndPassword(manager.getId(),manager.getPassword());
        if(m!=null){
            m.setToken(JWTUtil.generateToken(manager.getId()));
        }
        return m;
    }

//添加管理员
    @ApiOperation("管理员注册")
    @ApiImplicitParam(name = "manager",value = "管理员实体",required = true)
    @PostMapping("/register")
    public Manager register(@RequestBody Manager manager){
        Manager m=managerDao.findManagerByName(manager.getName());
        if(m==null){
            Manager m1=new Manager();
            m1.setName(manager.getName());
            m1.setPassword(manager.getPassword());
            managerDao.save(m1);
            //返回id
            m1.setId(managerDao.findManagerByNameAndPassword(manager.getName(),manager.getPassword()).getId());
            return m1;
        }
        return null;
    }

    //删除管理员
    @ApiOperation("删除管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "管理员id",required = true),
            @ApiImplicitParam(name = "password",value = "管理员密码",required = true)
    })
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Manager manager=managerDao.findManagerById(id);
        if(manager!=null){
            managerDao.deleteById(id);
            return "success";
        }else{
            return "fail";
        }
    }
}
