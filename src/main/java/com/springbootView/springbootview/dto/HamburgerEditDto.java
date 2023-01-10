package com.springbootView.springbootview.dto;

import com.springbootView.springbootview.model.Topping;
import java.util.List;


public class HamburgerEditDto {


    private long id;
    private String name;
    private int price;
    private List<Topping> toppings;

    public HamburgerEditDto() {
    }

    public HamburgerEditDto(String name, int price, List<Topping> toppings) {
        this.name = name;
        this.price = price;
        this.toppings = toppings;
    }

    public HamburgerEditDto(long id, String name, int price, List<Topping> toppings) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.toppings = toppings;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }




}
