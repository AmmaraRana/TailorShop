package com.example.adminpanel.Tailor.TailorModel;

public class ShipModel {
    private String AccountID;
    private String AccountNum;
    private String CustomerContact;
    private String Image;

    private String Paidammount;
    private String Status;


    private String sellerid;
private String id;



    // Default constructor (required for Firebase)
    public ShipModel() {
    }

    public ShipModel(String accountID, String accountNum, String customerContact, String image, String paidammount, String status, String sellerid,String id) {
        AccountID = accountID;
        AccountNum = accountNum;
        CustomerContact = customerContact;
        Image = image;
        Paidammount = paidammount;
        Status = status;
        this.id=id;
        this.sellerid = sellerid;
    }
// Getter and Setter methods


    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String accountID) {
        AccountID = accountID;
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

    public String getPaidammount() {
        return Paidammount;
    }

    public void setPaidammount(String paidammount) {
        Paidammount = paidammount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
