package com.example.adminpanel.Model;

public class Users {
    private String name,password,phone,address,image,FCM;

    public Users() {
    }

    public Users(String name, String password, String phone, String address, String image,String FCM) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.FCM=FCM;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFCM() {
        return FCM;
    }

    public void setFCM(String FCM) {
        this.FCM = FCM;
    }
}
