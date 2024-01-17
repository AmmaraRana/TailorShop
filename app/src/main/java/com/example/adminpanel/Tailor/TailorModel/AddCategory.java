package com.example.adminpanel.Tailor.TailorModel;

public class AddCategory {
    private String cId;
    private String categoryName;
    private String imageUrl;

    public AddCategory() {
    }

    public AddCategory(String cId, String categoryName, String imageUrl) {
        this.cId = cId;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
    }

    public String getCId() {
        return cId;
    }

    public void setCId(String cId) {
        this.cId = cId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
