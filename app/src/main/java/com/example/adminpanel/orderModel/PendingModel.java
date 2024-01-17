package com.example.adminpanel.orderModel;

public class PendingModel {
    private String TotalAmount,address,city,contactinfo,customrequirement,date,postalcode,state,time,name,price;

    public PendingModel() {
    }

    public PendingModel(String totalAmount, String address, String city, String contactinfo, String customrequirement, String date, String postalcode, String state, String time,String name) {
        TotalAmount = totalAmount;
        this.address = address;
        this.city = city;
        this.contactinfo = contactinfo;
        this.customrequirement = customrequirement;
        this.date = date;
        this.postalcode = postalcode;
        this.state = state;
        this.time = time;
        this.name=name;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactinfo() {
        return contactinfo;
    }

    public void setContactinfo(String contactinfo) {
        this.contactinfo = contactinfo;
    }

    public String getCustomrequirement() {
        return customrequirement;
    }

    public void setCustomrequirement(String customrequirement) {
        this.customrequirement = customrequirement;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
