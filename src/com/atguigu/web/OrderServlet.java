package com.atguigu.web;

import com.atguigu.pojo.*;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import com.atguigu.utils.JdbcUtils;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先获取Cart购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //获取UserId（用户在登录时保存到了Session域中）
        User loginUser = (User) request.getSession().getAttribute("user");
        //用户未登录会出现空指针异常
        if (loginUser == null) {
            //请求转发到登录页面
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            //return;不再执行下面的代码
            return;
        }
        Integer userId = loginUser.getId();
        //调用orderService.createOrder(Cart,UserId);生成订单
        String orderId = orderService.createOrder(cart, userId);
        //保存订单号
//        request.setAttribute("orderId",orderId);
        //请求转发到/pages/cart/checkout.jsp，（为了防止表单重复提交，建议使用重定向）
//        request.getRequestDispatcher("/pages/cart/checkout.jsp").forward(request,response);

        //保存订单号到sesssion域中
        request.getSession().setAttribute("orderId", orderId);
        //重定向不支持request域的共享
        response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");

    }

    protected void showAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取全部订单
        List<Order> orders = orderService.showAllOrders();
        //保存到request域中
        request.setAttribute("orders", orders);
        System.out.println(orders);
        //请求转发到/pages/manager/order_manager.jsp
        request.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(request, response);
    }

    /**
     * 订单发货
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void sendOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取发货订单
        String orderId = request.getParameter("orderId");
        //orderId不为空进行发货
        if (orderId != null) {
            orderService.sendOrder(orderId);
        }
        //页面重定向跳转
        response.sendRedirect(request.getHeader("Referer"));

    }

    /**
     * 查看订单详情
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void showOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单编号
        String orderId = request.getParameter("orderId");
        //获取订单详情
        List<OrderItem> orderItems = orderService.showOrderDetail(orderId);
        //保存到request域中
        request.setAttribute("orderItems", orderItems);
        //请求转发
        request.getRequestDispatcher("/pages/order/order_detail.jsp").forward(request, response);

    }

    /**
     * 查看我的订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void showMyOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User loginUser = (User) request.getSession().getAttribute("user");
//        System.out.println(loginUser.getId());
        List<Order> myOrders = orderService.showMyOrders(loginUser.getId());
        request.setAttribute("myOrders",myOrders);
        request.getRequestDispatcher("/pages/order/order.jsp").forward(request,response);
    }

        /**
         * 订单分页
         *
         * @param request
         * @param response
         * @throws ServletException
         * @throws IOException
         */
    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取请求的参数 pageNo和pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        //2、调用orderService.page(pageNo,pageSize)
        Page<Order> page = orderService.page(pageNo, pageSize);

        page.setUrl("/manager/orderServlet?action=page");

        //3、保存Page对象到Request域中
        request.setAttribute("page", page);
        //4、请求转发到/pages/manager/book_manager.jsp
        request.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(request, response);
    }
}
