package com.atguigu.service;

import com.atguigu.pojo.*;

import java.util.List;

public interface OrderService {
    /**
     * 创建订单
     * @param cart
     * @param userId
     * @return
     */
    public String createOrder(Cart cart,Integer userId);

    /**
     * 查询全部订单
     * @return
     */
    public List<Order> showAllOrders();

    /**
     * 订单分页
     * @param pageNo 分页页码
     * @param pageSize 分页数据数量
     * @return
     */
    public Page<Order> page(int pageNo, int pageSize);

    /**
     * 订单发货
     */
    public void sendOrder(String orderId);

    /**
     * 查看订单详情
     * @return
     */
    public List<OrderItem> showOrderDetail(String orderId);

    /**
     * 查询我的订单
     * @return
     * @param userId
     */
    public List<Order> showMyOrders(Integer userId);

    /**
     * 签收订单/确认收货
     */
    public void receiverOrder(String orderId);
}
