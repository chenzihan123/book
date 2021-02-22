package com.atguigu.pojo;

import java.math.BigDecimal;
import java.util.*;

/**
 * 购物车对象
 */
public class Cart {
//    private Integer totalCount;//总商品数量
//    private BigDecimal totalPrice;//总商品金额
    /**
     * key是商品编号
     * value，是商品信息
     */
    private Map<Integer,CartItem> items = new LinkedHashMap<Integer,CartItem>();//购物车商品

    public Cart() {
    }

    public Cart(Map<Integer, CartItem> items) {
        this.items = items;
    }

//    public Cart(Integer totalCount, BigDecimal totalPrice, Map<Integer,CartItem> items) {
//        this.totalCount = totalCount;
//        this.totalPrice = totalPrice;
//        this.items = items;
//    }

    public Integer getTotalCount() {
        Integer totalCount =0;
        //遍历map中的每一个键值对
        for (Map.Entry<Integer,CartItem>entry : items.entrySet()){
            totalCount += entry.getValue().getCount();
        }
        return totalCount;
    }

//    public void setTotalCount(Integer totalCount) {
//        this.totalCount = totalCount;
//    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer,CartItem>entry : items.entrySet()){
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }

//    public void setTotalPrice(BigDecimal totalPrice) {
//        this.totalPrice = totalPrice;
//    }

    public Map<Integer,CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer,CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }

    /**
     * 添加商品项
     * @param cartItem 购物车商品
     */
    public void addItem(CartItem cartItem){
        //首先查看购物车中是否已经添加过此商品，如果已添加，则数量累加，总金额更新，如果没有添加过，直接放到集合中即可
        CartItem item = items.get(cartItem.getId());
        //商品未添加过
        if (item == null){
            items.put(cartItem.getId(),cartItem);
        }else {
            //已经添加过此商品
            item.setCount(item.getCount() + 1);//数量累加
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));//更新总金额（商品价格 * 商品数量）
        }
    }

    /**
     * 删除商品项
     * @param id 商品id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }

    /**
     * 清空购物车
     */
    public void clear(){
        items.clear();
    }

    /**
     * 修改商品数量
     * @param id 商品编号
     * @param count 商品数量
     */
    public void updateCount(Integer id,Integer count){
        //首先查看购物车中是否有此商品，如果有，修改商品数量，更新总金额
        CartItem cartItem = items.get(id);
        if (cartItem != null){
            cartItem.setCount(count);//修改商品数量
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));//更新总金额
        }
    }
}
