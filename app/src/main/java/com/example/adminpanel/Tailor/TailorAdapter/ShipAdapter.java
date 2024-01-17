package com.example.adminpanel.Tailor.TailorAdapter;

public class ShipAdapter {
String Status,Paidammount,AccountID,sellerid,AccountNum,CustomerContact,Image;

    public ShipAdapter(String status, String paidammount, String accountID, String sellerid, String accountNum, String customerContact, String image) {
        Status = status;
        Paidammount = paidammount;
        AccountID = accountID;
        this.sellerid = sellerid;
        AccountNum = accountNum;
        CustomerContact = customerContact;
        Image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPaidammount() {
        return Paidammount;
    }

    public void setPaidammount(String paidammount) {
        Paidammount = paidammount;
    }

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getAccountNum() {
        return AccountNum;
    }

    public void setAccountNum(String accountNum) {
        AccountNum = accountNum;
    }

    public String getCustomerContact() {
        return CustomerContact;
    }

    public void setCustomerContact(String customerContact) {
        CustomerContact = customerContact;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}