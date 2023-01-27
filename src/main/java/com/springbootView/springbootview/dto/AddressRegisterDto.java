package com.springbootView.springbootview.dto;

public class AddressRegisterDto {

    private Long userId;
    private String city;
    private String street;
    private String houseNumber;
    private String zip;
    private String phone;

    public AddressRegisterDto() {
    }

    public AddressRegisterDto(String city, String street, String houseNumber, String zip, String phone) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zip = zip;
        this.phone = phone;
    }

    public AddressRegisterDto(Long userId, String city, String street, String houseNumber, String zip, String phone) {
        this.userId = userId;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zip = zip;
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

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
