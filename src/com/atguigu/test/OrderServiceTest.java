package com.atguigu.test;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.Page;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceTest {
    private OrderService orderService = new OrderServiceImpl();
    @Test
    public void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1, "java从入门到精通", 1, new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(1, "java从入门到精通", 1, new BigDecimal(1000),new BigDecimal(1000)));
        cart.addItem(new CartItem(2, "数据结构与算法", 1, new BigDecimal(100),new BigDecimal(100)));

        System.out.println(orderService.createOrder(cart, 1));
    }
    @Test
    public void showAllOrders() {
        List<Order> orders = orderService.showAllOrders();
//        System.out.println(orders);
        for (Order order : orders){
            System.out.println(order);
        }
    }
    @Test
    public void page(){
        Page<Order> orderPage = orderService.page(1, 4);
        for (Order order : orderPage.getItems()){
            System.out.println(order.getOrderId());
        }
    }
}