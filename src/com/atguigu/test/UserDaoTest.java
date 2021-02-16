package com.atguigu.test;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import com.atguigu.pojo.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDaoTest {

    @Test
    public void queryUserByUsername() {
        UserDao userDao = new UserDaoImpl();
        if (userDao.queryUserByUsername("admin") == null){
            System.out.println("用户可用！");
        }else {
            System.out.println("用户已存在！");
        }
    }

    @Test
    public void queryUserNameAndPassword() {
        UserDao userDao = new UserDaoImpl();
        if (userDao.queryUserNameAndPassword("admin","admin") != null) {
            System.out.println("用户已存在！");
        } else {
            System.out.println("用户可用！");
        }
    }

    @Test
    public void saveUser() {
        UserDao userDao = new UserDaoImpl();
        System.out.println(userDao.saveUser(new User(null,"ainx","admin","666@qq.com")));
    }
}