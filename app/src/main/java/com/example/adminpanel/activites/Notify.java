package com.example.adminpanel.activites;

public class Notify {
    String CustomerContact, DeliveryDays,Paidammount,id, sellerid;

    public Notify() {
    }

    public Notify(String customerContact, String deliveryDays, String paidammount, String id, String sellerid) {
        CustomerContact = customerContact;
        DeliveryDays = deliveryDays;
        Paidammount = paidammount;
        this.id = id;
        this.sellerid = sellerid;
    }

    public String getCustomerContact() {
        return CustomerContact;
    }

    public void setCustomerContact(String customerContact) {
        CustomerContact = customerContact;
    }

    public String getDeliveryDays() {
        return DeliveryDays;
    }

    public void setDeliveryDays(String deliveryDays) {
        DeliveryDays = deliveryDays;
    }

    public String getPaidammount() {
        return Paidammount;
    }

    public void setPaidammount(String paidammount) {
        Paidammount = paidammount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }
}
