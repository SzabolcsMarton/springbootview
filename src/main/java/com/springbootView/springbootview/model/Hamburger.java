package com.springbootView.springbootview.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Hamburger implements  Comparable<Hamburger>{

    @Id
    @SequenceGenerator(name = "seqGenHamburger", sequenceName = "hamburgerIdSeq", initialValue = 10001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenHamburger")
    private long id;
    private String name;
    private int price;
    @OneToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "hamburgers_toppings", joinColumns = {@JoinColumn(name = "hamburger_id")}, inverseJoinColumns = {
            @JoinColumn(name = "topping_id")})
    private List<Topping> toppings;

    public Hamburger() {
    }

    public Hamburger(String name, int price, List<Topping> toppings) {
        this.name = name;
        this.price = price;
        this.toppings = toppings;
    }

    public Hamburger(long id, String name, int price, List<Topping> toppings) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hamburger hamburger = (Hamburger) o;

        return name.equals(hamburger.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Hamburger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", toppings=" + toppings +
                '}';
    }

    @Override
    public int compareTo(Hamburger o) {
        return this.getName().compareTo(o.getName());
    }
}
