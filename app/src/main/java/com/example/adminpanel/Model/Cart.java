package com.example.adminpanel.Model;

public class Cart {

    private String category,price,contact,detail,fabric
            ,name,pID,quantity,shop,discount,sid,selectedImage,
            image1,image2,image3;

    public Cart(){

    }

    public Cart(String category, String price, String contact, String detail,
                String fabric, String name, String pID, String quantity, String shop,
                String discount,String sid,String selectedImage,
    String image1,String image2,String image3 ) {
        this.category = category;
        this.price = price;
        this.contact = contact;
        this.detail = detail;
        this.fabric = fabric;
        this.name = name;
        this.pID = pID;
        this.quantity = quantity;
        this.shop = shop;
        this.discount = discount;
        this.sid=sid;
        this.selectedImage=selectedImage;
        this.image1=image1;
        this.image2=image2;
        this.image3=image3;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpID() {
        return pID;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(String selectedImage) {
        this.selectedImage = selectedImage;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }
}
