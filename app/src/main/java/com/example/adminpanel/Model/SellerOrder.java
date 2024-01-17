package com.example.adminpanel.Model;

public class SellerOrder {
    String LegLength,
            armLength,
            neck_circumference,

    shoulderwidth;
    private String TotalAmount, address, city, contactinfo, customrequirement, date, name, postalcode, sid, state, status, pid,
            size_image,

    time;


    public SellerOrder() {
    }

    public SellerOrder(String totalAmount, String address, String city, String contactinfo, String customrequirement, String date, String name, String postalcode, String sid, String state, String status, String time, String pid, String size_image
                        ,  String LegLength,
                       String  armLength,
                       String  neck_circumference,

                       String  shoulderwidth
    ) {
        TotalAmount = totalAmount;
        this.address = address;
        this.city = city;
        this.contactinfo = contactinfo;
        this.customrequirement = customrequirement;
        this.date = date;
        this.name = name;
        this.postalcode = postalcode;
        this.sid = sid;
        this.state = state;
        this.status = status;
        this.time = time;
        this.pid = pid;
        this.size_image = size_image;
        this.armLength=armLength;
        this.shoulderwidth=shoulderwidth;
        this.neck_circumference=neck_circumference;
        this.armLength=armLength;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLegLength() {
        return LegLength;
    }

    public void setLegLength(String legLength) {
        LegLength = legLength;
    }

    public String getArmLength() {
        return armLength;
    }

    public void setArmLength(String armLength) {
        this.armLength = armLength;
    }

    public String getNeck_circumference() {
        return neck_circumference;
    }

    public void setNeck_circumference(String neck_circumference) {
        this.neck_circumference = neck_circumference;
    }

    public String getShoulderwidth() {
        return shoulderwidth;
    }

    public void setShoulderwidth(String shoulderwidth) {
        this.shoulderwidth = shoulderwidth;
    }

    public String getSize_image() {
        return size_image;
    }

    public void setSize_image(String size_image) {
        this.size_image = size_image;
    }
}
