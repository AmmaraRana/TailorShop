package com.example.adminpanel.Model;

public class Billing {
    String Coninfo, ProductName, ShipmentCity, ShipmentAddress, Status, bill_amount, deliveredwithin, productId, sellerid,
            billid
                    ;

    public Billing() {
    }

    public Billing(String coninfo, String productName, String shipmentAddress,
                   String status, String bill_amount, String deliveredwithin, String productId, String ShipmentCity, String sellerid,String billid) {
        Coninfo = coninfo;
        ProductName = productName;
        ShipmentAddress = shipmentAddress;
        Status = status;
        this.bill_amount = bill_amount;
        this.deliveredwithin = deliveredwithin;
        this.productId = productId;
        this.ShipmentCity = ShipmentCity;
        this.sellerid = sellerid;
        this.billid=billid;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public String getConinfo() {
        return Coninfo;
    }

    public void setConinfo(String coninfo) {
        Coninfo = coninfo;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getShipmentAddress() {
        return ShipmentAddress;
    }

    public void setShipmentAddress(String shipmentAddress) {
        ShipmentAddress = shipmentAddress;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getDeliveredwithin() {
        return deliveredwithin;
    }

    public void setDeliveredwithin(String deliveredwithin) {
        this.deliveredwithin = deliveredwithin;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getShipmentCity() {
        return ShipmentCity;
    }

    public void setShipmentCity(String shipmentCity) {
        ShipmentCity = shipmentCity;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }
}
