package com.atguigu.test;

import com.atguigu.dao.OrderDao;
import com.atguigu.dao.impl.OrderDaoImpl;
import com.atguigu.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class OrderDaoTest {
    private OrderDao orderDao = new OrderDaoImpl();
    @Test
    public void saveOrder() {
        orderDao.saveOrder(new Order("1234567891",new Date(),new BigDecimal(100),0, 1));
    }
    @Test
    public void showAllOrders(){
        List<Order> orders = orderDao.showAllOrders();
        for (Order order : orders){
            System.out.println(order);
        }
    }
    @Test
    public void queryForPageTotalCount(){
        Integer pageTotalCount = orderDao.queryForPageTotalCount();
        System.out.println(pageTotalCount);
    }
    @Test
    public void queryForPageItems(){
        List<Order> orders = orderDao.queryForPageItems(1, 4);
        for (Order order : orders){
            System.out.println(order);
        }
    }
    @Test
    public void changeOrderStatus(){
        int changeCount = orderDao.changeOrderStatus("1234567891", 2);
        if (changeCount > 0){
            System.out.println("修改成功！");
        }
    }
    @Test
    public void queryOrderByUserId(){
        List<Order> orders = orderDao.queryOrderByUserId(4);
        for (Order order : orders){
            System.out.println(order);
        }
    }
}