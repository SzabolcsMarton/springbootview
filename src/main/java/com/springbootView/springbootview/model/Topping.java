package com.springbootView.springbootview.model;

import javax.persistence.*;

@Entity
public class Topping {

    @Id
    @SequenceGenerator(name = "seqGenTopping", sequenceName = "toppingIdSeq", initialValue = 20001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenTopping")
    private long id;
    private String name;

    public Topping() {
    }

    public Topping(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Topping(String name) {
        this.name = name;
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


}
