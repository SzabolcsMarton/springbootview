package com.springbootView.springbootview.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;



@Entity
@Table(name = "orderitem")
public class OrderItem {

    @Id
    @SequenceGenerator(name = "seqGenOrderItem", sequenceName = "orderItemIdSeq", initialValue = 50001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenOrderItem")
    @Column(name = "order_item_id")
    private Long orderItemId;
    @Column(name = "hamburger_name")
    private String hamburgerName;
    private int quantity;
    private int price;
    private int sumOfPrice;
    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public OrderItem() {
    }

    public OrderItem(String hamburgerName, int quantity, int price, Cart cart) {
        this.hamburgerName = hamburgerName;
        this.quantity = quantity;
        this.price = price;
        this.sumOfPrice = quantity * price;
        this.cart = cart;
    }

    public String getHamburgerName() {
        return hamburgerName;
    }

    public void setHamburgerName(String hamburgerName) {
        this.hamburgerName = hamburgerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getSumOfPrice() {
        return sumOfPrice;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getId() {
        return orderItemId;
    }

    public void setId(Long id) {
        this.orderItemId = id;
    }

    public void calculateSumOfPrice(){
        sumOfPrice = this.quantity * this.price;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setSumOfPrice(int sumOfPrice) {
        this.sumOfPrice = sumOfPrice;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
