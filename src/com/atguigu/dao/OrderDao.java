package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;

public interface OrderDao {
    public int saveOrder(Order order);

    public List<Order> showAllOrders();

    public Integer queryForPageTotalCount();

    public List<Order> queryForPageItems(int begin, int pageSize);

    public int changeOrderStatus(String orderId,Integer status);

    public List<Order> queryOrderByUserId(Integer userId);

}
