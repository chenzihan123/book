package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {

    private UserService userService = new UserServiceImpl();

    /**
     * 处理登录的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //调用userService.Login()登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));
        if (loginUser != null){
            //用户存在，登录成功
            //保存用户的登录信息到Session域中
            request.getSession().setAttribute("user",loginUser);
            System.out.println(loginUser);
            //跳到成功页面login_success.jsp
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request,response);
        }else {//如果等于null，说明登录失败！
            //把错误信息，和回显信息的表单项信息，保存到Request域中
            request.setAttribute("msg","用户名或密码错误！");
            request.setAttribute("username",username);
            //跳转登录页面
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);

        }
    }
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1、销毁 Session 中用户登录的信息（或者销毁 Session）
        request.getSession().invalidate();
        // 2、重定向到首页（或登录页面）。
        response.sendRedirect(request.getContextPath());
    }

        /**
         * 处理注册功能
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码功能
        //获取session中的验证码
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除Session中的验证码
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        //获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        //获取输入的验证码
        String code = request.getParameter("code");

        // 2、检查 验证码是否正确 === 写死,要求验证码为:abcde
        if (token != null && token.equalsIgnoreCase(code)){
            //3、检查用户名是否可用
            if (userService.existsUserName(username)){
                System.out.println("用户名[" + username + "]已存在!");

                //把会先信息，保存到Request域中
                request.setAttribute("msg","用户名已存在！");
                request.setAttribute("username",username);
                request.setAttribute("email",email);

                //跳转回注册页面
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request,response);
            }else {
                // 可用
                // 调用 Sservice 保存到数据库
//                userService.registUser(new User(null,username,password,email));

                WebUtils.copyParamToBean(request.getParameterMap(),new User());

                // 跳到注册成功页面 regist_success.jsp
                request.getRequestDispatcher("/pages/user/regist_success.jsp").forward(request,response);

            }
        }else {
            // 把回显信息，保存到 Request 域中
            request.setAttribute("msg", "验证码错误！！");
            request.setAttribute("username", username);
            request.setAttribute("email", email);

            System.out.println("验证码[" + code + "]错误");
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
        }
    }

//        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//         String action = request.getParameter("action");
//            //1、判断action的值调用不同的方法
////         if ("login".equals(action)){
////             login(request,response);
////         }else if ("regist".equals(action)){
////             regist(request,response);
////         }
//
////            2、使用反射优化大量的else if代码
//            try {
//                // 获取 action 业务鉴别字符串，获取相应的业务 方法反射对象
//                Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
//
//                //调用目标业务 方法
//                method.invoke(this,request,response);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//
//        }

        protected void ajaxExistsUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //获取请求参数
            String username = request.getParameter("username");
            //调用userService.existsUsername()
            boolean existsUserName = userService.existsUserName(username);
            //把返回的结果封装成Map对象
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("existsUserName",existsUserName);

            Gson gson = new Gson();
            String json = gson.toJson(resultMap);

            response.getWriter().write(json);
        }


}
