package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");

        //2.检查验证码是否正确   --> 写死，要求验证码为abcde
        //正确
        if ("abcde".equalsIgnoreCase(code)) {
            //3.检查用户名是否可用
            if (userService.existsUserName(username)) {
                //true，用户已存在，用户名不可用
                System.out.println("用户名[ " + username + " ]已存在");
                //跳回注册页面
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            } else {
                //用户名不存在可用
                //调用service保存到数据库
                userService.registUser(new User(null, username, password, email));
                //跳转到注册成功页面 regist_success.jsp
                request.getRequestDispatcher("/pages/user/regist_success.jsp").forward(request, response);
            }
        } else {
            //不正确
            System.out.println("验证码[ " + code + " ]错误");
            //跳回注册页面
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);

        }


    }

}
