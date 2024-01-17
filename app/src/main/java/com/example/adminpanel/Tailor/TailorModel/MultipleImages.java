package com.example.adminpanel.Tailor.TailorModel;

public class MultipleImages {
    private String imageURL;

    // Empty constructor for Firebase
    public MultipleImages() {
    }

    // Constructor with imageURL parameter
    public MultipleImages(String imageURL) {
        this.imageURL = imageURL;
    }

    // Getter and setter for imageURL
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}



