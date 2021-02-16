package com.atguigu.web;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取页面提交信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User loginUser = userService.login(new User(null, username, password, null));
        //对提交的信息验证
        if (loginUser != null){
            //用户存在,登陆成功,跳转登录成功页面
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request,response);
        }else {
            //把错误信息和回显的表单信息，保存到Request域中
            request.setAttribute("msg","用户名或密码错误！");
            request.setAttribute("username",username);
            //用户不存在，登陆失败。跳转登录页面
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
        }
    }
}
