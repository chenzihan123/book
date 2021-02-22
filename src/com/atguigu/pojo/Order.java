package com.atguigu.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单类
 * String orderId;//订单号
 * Date createTime;//下单时间
 * BigDecimal price;//金额
 * 0 未发货，1   已发货，2   已签收
 * Integer status = 0;//订单状态
 * Integer userId;//用户编号
 */
public class Order {
    private String orderId;//订单号
    private Date createTime;//下单时间
    private BigDecimal price;//金额
    //0 未发货，1   已发货，2   已签收
    private Integer status = 0;//订单状态
    private Integer userId;//用户编号

    public Order() {
    }

    public Order(String orderId, Date createTime, BigDecimal price, Integer status, Integer userId) {
        this.orderId = orderId;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }
}
