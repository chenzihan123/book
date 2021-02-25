package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImp;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet {
    private BookService bookService = new BookServiceImp();

    /**
     * 添加商品项
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("添加成功");
//        System.out.println("商品编号：" + request.getParameter("id"));
        //获取请求的参数 商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        //调用bookService.queryBookById(id):Book得到图书信息
        Book book = bookService.queryBookById(id);
        //把图书信息，转换为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //调用Cart.addItem(CartItem);添加商品项
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            request.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
//        System.out.println(cart);
        //最后一个添加商品的名称
        request.getSession().setAttribute("lastName",cartItem.getName());
        //重定向回商品列表页面
//        response.sendRedirect(request.getContextPath());
        response.sendRedirect(request.getHeader("Referer"));
    }

    /**
     * 添加商品项
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void ajaxAddItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("添加成功");
//        System.out.println("商品编号：" + request.getParameter("id"));
        //获取请求的参数 商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        //调用bookService.queryBookById(id):Book得到图书信息
        Book book = bookService.queryBookById(id);
        //把图书信息，转换为CartItem商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        //调用Cart.addItem(CartItem);添加商品项
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            request.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
//        System.out.println(cart);
        //最后一个添加商品的名称
        request.getSession().setAttribute("lastName",cartItem.getName());
        //重定向回商品列表页面
//        response.sendRedirect(request.getContextPath());
//        response.sendRedirect(request.getHeader("Referer"));
        //6.返回购物车总的商品数量和最后一个添加的商品名称
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String resultMapJsonString = gson.toJson(resultMap);
        response.getWriter().write(resultMapJsonString);
    }

    /**
     * 删除商品项
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        //获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (cart != null){
            //删除购物车商品项
            cart.deleteItem(id);
            //重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session域中的cart对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //购物车不为空
        if (cart != null){
            //清空购物车
            cart.clear();
            //重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    /**
     * 修改商品数量
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求的参数 商品编号、商品数量
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 0);
        //获取Cart购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null){
            //修改商品数量
            cart.updateCount(id,count);
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
}
