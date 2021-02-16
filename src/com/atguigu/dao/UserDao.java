package com.atguigu.dao;

import com.atguigu.pojo.User;

/**
 * 数据库操作由实现的功能决定
 */
public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 如果返回为null，说明没有这个用户，反之亦然
     */
    public User queryUserByUsername(String username);

    /**
     * 根据用户名和密码查询用户信息
     * @param username
     * @param password
     * @return 如果返回为null，说明没有这个用户，反之亦然
     */
    public User queryUserNameAndPassword(String username,String password);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    public int saveUser(User user);
}
