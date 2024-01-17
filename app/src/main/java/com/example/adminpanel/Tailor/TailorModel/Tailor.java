package com.example.adminpanel.Tailor.TailorModel;

public class Tailor {
    String Account_ID,Account_Number,name,phone,shopaddress,image,ShopName
            ,email
            ,sellerCity
            ,uid;


    public Tailor() {
    }

    public Tailor(String account_ID, String account_Number, String name, String phone, String shopaddress, String image, String shopName, String email, String sellerCity, String uid) {
        Account_ID = account_ID;
        Account_Number = account_Number;
        this.name = name;
        this.phone = phone;
        this.shopaddress = shopaddress;
        this.image = image;
        ShopName = shopName;
        this.email = email;
        this.sellerCity = sellerCity;
        this.uid = uid;
    }

    public String getAccount_ID() {
        return Account_ID;
    }

    public void setAccount_ID(String account_ID) {
        Account_ID = account_ID;
    }

    public String getAccount_Number() {
        return Account_Number;
    }

    public void setAccount_Number(String account_Number) {
        Account_Number = account_Number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSellerCity() {
        return sellerCity;
    }

    public void setSellerCity(String sellerCity) {
        this.sellerCity = sellerCity;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
