package com.atguigu.dao.impl;

import com.atguigu.dao.OrderDao;
import com.atguigu.pojo.Order;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {

    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`)values(?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }

    @Override
    public List<Order> showAllOrders() {
        String sql = "select `order_id` orderId, `create_time` createTime, `price`, `status`, `user_id` from t_order";
        List<Order> orders = queryForList(Order.class, sql);
        return orders;
    }

    @Override
    public Integer queryForPageTotalCount() {
        String sql = "select count(*) from t_order";
        Number count = (Number) queryForSingleValue(sql);
        return count.intValue();
    }

    @Override
    public List<Order> queryForPageItems(int begin, int pageSize) {
        String sql = "select `order_id` orderId, `create_time` createTime, `price`, `status`, `user_id` from t_order limit ?,?";
        return queryForList(Order.class,sql,begin,pageSize);
    }

    @Override
    public int changeOrderStatus(String orderId, Integer status) {
        String sql = "update t_order set status=? where order_id=?";
        return update(sql,status,orderId);
    }

    @Override
    public List<Order> queryOrderByUserId(Integer userId) {
        String sql = "select `order_id` orderId, `create_time` createTime, `price`, `status`, `user_id` userId from t_order where user_id=?";
        List<Order> orders = queryForList(Order.class, sql,userId);
        return orders;
    }
}
