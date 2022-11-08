package com.example.service;

import com.example.dao.IManagerDao;
import com.example.dao.IStudentDao;
import com.example.entity.Manager;
import com.example.entity.Student;
import com.example.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;

//POST MAN404原因：漏了@RestController，没有控制器肯定访问不到
//只返回对象，JSON形式
@RestController
@RequestMapping("/student")
//完成跨域
@CrossOrigin
public class StudentService {
    @Autowired
    private IStudentDao studentDao;

    //@ApiOperation相当于是注释一下这个方法是干啥的
    @ApiOperation("根据id获取学生")
    @ApiImplicitParam(name = "id",value = "学生id",required = true)
    @GetMapping("/get/{id}")
//    @GetMapping("/get/{id}")
//    public Student getStudent(@PathVariable int id)
    //获取路径上的参数必须加上@PathVariable注解，不然只能拿到?后面的
    public Student getStudent(@PathVariable("id") int id){
          Student student=studentDao.findStudentById(id);
          return  student;
    }

    @ApiOperation("根据姓名获取学生")
    @ApiImplicitParam(name = "name",value = "学生姓名",required = true)
    @GetMapping("/getByName/{name}")
//    @GetMapping("/get/{id}")
//    public Student getStudent(@PathVariable int id)
    //获取路径上的参数必须加上@PathVariable注解，不然只能拿到?后面的
    public Student getStudent(@PathVariable("name") String name){
        Student student=studentDao.findStudentByName(name);
        return student;
    }

    //获取学生列表的同时进行分页操作
    @ApiOperation("获取学生列表并分页")
    @GetMapping("/getAll/{page}/{size}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "第几页",required = true),
            @ApiImplicitParam(name = "size",value = "每页信息条数",required = true)
    })
    public Page<Student> getAAllStudents(@PathVariable("page") int page, @PathVariable("size") int size){
        Pageable pageable= PageRequest.of(page-1,size);
        return studentDao.findAll(pageable);
    }


//    @只能用来接收json格式数据， RequestBody 把json转成Java对象
    //json对象里的数据要和实体类里的数据名称保持一致，数据类型保持一致
    //postman中"Unsupported Media Type",原因：raw 旁边的框框选成json
    @ApiOperation("学生注册")
    @ApiImplicitParam(name = "student",value = "学生实体",required = true)
    @PostMapping("/register")
    public String register(@RequestBody Student student){
        Student s1=studentDao.findStudentByName(student.getName());
        if(s1==null){
            Student s=new Student();
            s.setName(student.getName());
            s.setPassword(student.getPassword());
            studentDao.save(s);
            return "success";
        }else{
            return "fail";
        }
    }

    @ApiOperation("更新密码或姓名`")
    @ApiImplicitParam(name = "student",value = "学生实体",required = true)
    @PutMapping( "/update")
    public String update(@RequestBody Student student){
        //能够又HttpServletRequest.getParam获取到参数

        Student s1=studentDao.findStudentById(student.getId());
        if(s1!=null){
            studentDao.updateStudentNameAndPasswordById(student.getPassword(),student.getName(),student.getId());
            return "success";
        }else{
            return "fail";
        }
    }

    @ApiOperation("删除学生")
    @ApiImplicitParam(name = "id",value = "学生id",required = true)
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        Student student=studentDao.findStudentById(id);
        if(student!=null){
            studentDao.delete(student);
            return"success";
        }
        else {
            return "fail ";
        }
    }
    @ApiOperation("根据token进行权限设置")
    @ApiImplicitParam(name = "request",value = "HttpServletRequest",required = true)
    @GetMapping("/check")
    public Boolean check(HttpServletRequest request){
        String token=request.getHeader("token");
        System.out.println(JWTUtil.checkByToken(token));
        return JWTUtil.checkByToken(token);
    }



    //session认证流程
    //浏览器向服务器发送用户名/密码申请认证
    //服务器认证完毕后，在当前会话(session)保存用户的信息,返回一个session_id给浏览器并放到cookie里面
    //随后的每一次请求，通过cookie把session_id发给服务器
    //服务器通过session_id找到之前的数据
    //问题：session的共享问题，比如有2台服务器，一次请求被分配给了A，里面保存了本次请求的session,然后第二次请求被分配给了B，用户本来已经
    //登录过了，但session没有共享，所以在B上又的存一次session
    //一是session持久化,放到数据库或是Redis等
    //二是可以用Token认证(无状态方式)同时解决了跨域问题，因为cookie不能共享(除非cors)
    //服务器不再保存session,所有的数据都保存在服务器，每次请求都发送给服务器

    //Token认证流程(重点在于Token的生成，需要另一台服务器)
    //浏览器向服务器发送用户名/密码申请认证
    //认证通过后，服务器签发一个Token给浏览器，浏览器把Token放到cookie或localStorage里 最好是放在HTTP头部的Authrization里面
    //之后的每一次请求，浏览器都携带Token
    //服务器收到请求并验证完毕Token后返回数据

}
