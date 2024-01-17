package com.example.adminpanel.Model;

public class Product {

    String name, category, description,fabric1,fabric3,
            fabric2, image, pID, price, sShop, size, time, date, sid;
    public String imageURL;
    String sex, Type, SellerCity, sellerAddress, sellerPhone;


    public Product() {
    }


    public Product(String name, String category, String description, String fabric1, String fabric3, String fabric2, String image, String pID, String price, String sShop, String size, String time, String date, String sid, String imageURL, String sex, String type, String sellerCity, String sellerAddress, String sellerPhone) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.fabric1 = fabric1;
        this.fabric3 = fabric3;
        this.fabric2 = fabric2;
        this.image = image;
        this.pID = pID;
        this.price = price;
        this.sShop = sShop;
        this.size = size;
        this.time = time;
        this.date = date;
        this.sid = sid;
        this.imageURL = imageURL;
        this.sex = sex;
        Type = type;
        SellerCity = sellerCity;
        this.sellerAddress = sellerAddress;
        this.sellerPhone = sellerPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFabric1() {
        return fabric1;
    }

    public void setFabric1(String fabric1) {
        this.fabric1 = fabric1;
    }

    public String getFabric3() {
        return fabric3;
    }

    public void setFabric3(String fabric3) {
        this.fabric3 = fabric3;
    }

    public String getFabric2() {
        return fabric2;
    }

    public void setFabric2(String fabric2) {
        this.fabric2 = fabric2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getsShop() {
        return sShop;
    }

    public void setsShop(String sShop) {
        this.sShop = sShop;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSellerCity() {
        return SellerCity;
    }

    public void setSellerCity(String sellerCity) {
        SellerCity = sellerCity;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }
}