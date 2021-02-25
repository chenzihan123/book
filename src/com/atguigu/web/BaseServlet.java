package com.atguigu.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 解决post请求中文乱码问题
        // 一定要在获取请求参数之前调用才有效
        request.setCharacterEncoding("UTF-8");
        // 解决响应中文乱码
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        //            2、使用反射优化大量的else if代码
        try {
            // 获取 action 业务鉴别字符串，获取相应的业务 方法反射对象
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);

            //调用目标业务 方法
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);//把异常抛给 Filter 过滤器
        }
    }

}
