package com.atguigu.service;


import com.atguigu.pojo.User;

public interface UserService {
    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user);

    /**
     * 用户登录
     * @return
     */
    public User login(User user);

    /**
     * 判断用户是否可用
     * @param username
     * @return 返回true表示用户已存在，返回false表示用户可用
     */
    public boolean existsUserName(String username);
}
