package com.atguigu.test;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {
    UserService userService = new UserServiceImpl();
    @Test
    public void registUser() {
        userService.registUser(new User(null,"admintest1","admintest1","admintest1@qq.com"));
    }

    @Test
    public void login() {
        if(userService.login(new User(null,"admintest1","admintest1","admintest1@qq.com")) != null){
            System.out.println("登录成功！");
        }else {
            System.out.println("登录成功！");
        }
    }

    @Test
    public void existsUserName() {
        if(userService.existsUserName("admintest1")){
            System.out.println("用户已存在！");
        }else {
            System.out.println("用户不存在！");
        }
    }
}