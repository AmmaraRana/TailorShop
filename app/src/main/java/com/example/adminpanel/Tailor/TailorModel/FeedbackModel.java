package com.example.adminpanel.Tailor.TailorModel;

public class FeedbackModel {
    String FeedbackText,Customer;
    Float Rating;
    public FeedbackModel() {

    }

    public FeedbackModel(Float rating, String feedbackText, String customer) {
        Rating = rating;
        FeedbackText = feedbackText;
        Customer = customer;
    }

    public Float getRating() {
        return Rating;
    }

    public void setRating(Float rating) {
        Rating = rating;
    }

    public String getFeedbackText() {
        return FeedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        FeedbackText = feedbackText;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }
}
