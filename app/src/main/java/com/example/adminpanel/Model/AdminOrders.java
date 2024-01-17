package com.example.adminpanel.Model;

public class AdminOrders {
    private String TotalAmount ,address,city,contactinfo,date,name,time,state;
    public AdminOrders() {
    }

    public AdminOrders(String totalAmount, String address, String city, String contactinfo, String date, String name, String time, String state) {
        TotalAmount = totalAmount;
        this.address = address;
        this.city = city;
        this.contactinfo = contactinfo;
        this.date = date;
        this.name = name;
        this.time = time;
        this.state = state;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
