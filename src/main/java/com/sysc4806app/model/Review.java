package com.sysc4806app.model;

public class Review {

    private int rating;
    private String text;
    private Product product;

    public Review(int rating, String text, Product product) {
        this.rating = rating;
        this.text = text;
        this.product = product;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
