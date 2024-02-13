package com.springbootView.springbootview.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class User {
    @Id
    @SequenceGenerator(name = "seqGenUser", sequenceName = "userIdSeq", initialValue = 70001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenUser")
    @Column(name = "user_id")
    private Long userId;
    private String name;
    //@Email(regexp = "^[\\\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$")
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "users_carts", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "cart_id")})
    private List<Cart> orders = new ArrayList<>();
    private LocalDate registered;
    private boolean vip;


    public User() {
    }

    public User(String name, String email, String password, List<Role> roles, Address address, List<Cart> orders, LocalDate registered, boolean vip) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.address = address;
        this.orders = orders;
        this.registered = registered;
        this.vip = vip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public List<Cart> getOrders() {
        return orders;
    }

    public void setOrders(List<Cart> orders) {
        this.orders = orders;
    }

    public void addOrder(Cart cart){
        this.orders.add(cart);
    }

    public void removeOrder(Cart cart){
        this.orders.remove(cart);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean hasVip) {
        this.vip = hasVip;
    }
}
