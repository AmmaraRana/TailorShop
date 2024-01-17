package com.example.adminpanel;

public class ProductModel {
    private String Category;
    private String Title;
    private String Price;
    private String Fabric;
    private String Description;
    private String Image;
    private  boolean  show;

    public ProductModel() {
    }

    public ProductModel(String category, String title, String price, String fabric, String description, String image, boolean show) {
        Category = category;
        Title = title;
        Price = price;
        Fabric = fabric;
        Description = description;
        Image = image;
        this.show = show;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getFabric() {
        return Fabric;
    }

    public void setFabric(String fabric) {
        Fabric = fabric;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
