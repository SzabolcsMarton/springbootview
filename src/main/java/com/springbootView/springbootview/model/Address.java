package com.springbootView.springbootview.model;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @SequenceGenerator(name = "seqGenAddress", sequenceName = "addressIdSeq", initialValue = 40001, allocationSize = 1)
    @GeneratedValue(generator = "seqGenAddress")
    @Column(name = "address_id")
    private long addressId;
    private String name;
    private String city;
    private String street;
    private String houseNumber;
    private String zip;
    private String phone;

    public Address() {
    }

    public Address(String name, String city, String street, String houseNumber, String zip, String phone) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zip = zip;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long id) {
        this.addressId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
