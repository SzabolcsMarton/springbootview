package com.springbootView.springbootview.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @SequenceGenerator(name = "seqGenCart", sequenceName = "cartIdSeq", initialValue = 30001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenCart")
    @Column(name = "cart_id")
    private Long cartId;
    private String name;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "cart_orderitems", joinColumns = {@JoinColumn(name = "cart_id")}, inverseJoinColumns = {
            @JoinColumn(name = "order_item_id")})
    private List<OrderItem> orderItems = new ArrayList<>();
    private boolean delivery;
    private double sumOfAllItemPrices;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private boolean vipUser = false;
    private String address;
    private LocalDateTime timeOfOrder = LocalDateTime.now();


    public Cart() {
    }

    public Cart(List<OrderItem> orderItems, boolean delivery, String address, User user, boolean vipUser) {
        this.orderItems = orderItems;
        this.delivery = delivery;
        this.sumOfAllItemPrices = getTotalPrice(orderItems, delivery);
        this.address = address;
        this.name = user.getName();
        this.user = user;
        this.vipUser = vipUser;

    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public void addItem(OrderItem item) {
        this.orderItems.add(item);
        this.sumOfAllItemPrices += item.getSumOfPrice();
    }

    public void removeItem(OrderItem item) {
        this.orderItems.remove(item);
        this.sumOfAllItemPrices -= item.getSumOfPrice();
    }

    private int getTotalPrice(List<OrderItem> orderItems, boolean delivery) {
        int sum = delivery ? 500 : 0;
        for (OrderItem actual : orderItems) {
            sum += actual.getSumOfPrice();
        }
        return sum;
    }

    public double getSumOfAllItemPrices() {
        return sumOfAllItemPrices;
    }

    public void setSumOfAllItemPrices(double sumOfAllItemPrices) {
        this.sumOfAllItemPrices = sumOfAllItemPrices;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public LocalDateTime getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(LocalDateTime timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(User user) {
        this.user = user;
        this.name = user.getName();
    }

    public boolean isVipUser() {
        return vipUser;
    }

    public void setVipUser(boolean vipUser) {
        this.vipUser = vipUser;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", name='" + name + '\'' +
                ", orderItems=" + orderItems +
                ", delivery=" + delivery +
                ", sumOfAllItemPrices=" + sumOfAllItemPrices +
                ", user=" + user +
                ", address='" + address + '\'' +
                ", timeOfOrder=" + timeOfOrder +
                '}';
    }
}
