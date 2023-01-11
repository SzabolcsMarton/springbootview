package com.springbootView.springbootview.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Cart {

    @Id
    @SequenceGenerator(name = "seqGenCart", sequenceName = "cartIdSeq", initialValue = 30001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenCart")
    @Column(name = "cart_id")
    private Long cartId;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "cart_orderitems", joinColumns = {@JoinColumn(name = "cart_id")}, inverseJoinColumns = {
            @JoinColumn(name = "order_item_id")})
    private List<OrderItem> orderItems = new ArrayList<>();
    private boolean delivery;
    private int sumOfAllItemPrices;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private Date timeOfOrder = new Date();


    public Cart() {
    }

    public Cart(List<OrderItem> orderItems, boolean delivery, int sumOfAllItemPrices, Address address) {
        this.orderItems = orderItems;
        this.delivery = delivery;
        this.sumOfAllItemPrices = getTotalPrice(orderItems, delivery);
        this.address = address;
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

    public int getSumOfAllItemPrices() {
        return sumOfAllItemPrices;
    }

    public void setSumOfAllItemPrices(int sumOfAllItemPrices) {
        this.sumOfAllItemPrices = sumOfAllItemPrices;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Date getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(Date timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + cartId +
                ", orderItems=" + orderItems +
                ", delivery=" + delivery +
                ", sumOfAllItemPrices=" + sumOfAllItemPrices +
                ", address=" + address +
                ", timeOfOrder=" + timeOfOrder +
                '}';
    }



}
