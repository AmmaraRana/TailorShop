package com.example.adminpanel;

public class ImageData {
    private String id;
    private String imageUrl;

    // Required default constructor for Firebase
    public ImageData() {
    }

    public ImageData(String id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    // Getter and setter methods for Firebase
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
