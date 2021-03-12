package com.sysc4806app.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    private static Review review;
    private static int rating;
    private static String text;
    private static Product product;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        rating = 5;
        text = "This product is very good";
        user = new User("tester");
        product = new Product("www.link.com", "Orange", "It's orange.", ProductType.ICE, ProductChain.DQ);
        review = new Review(rating, text, product, user);
    }

    @Test
    void getSetRating() {
        int newRating = 1;
        assertEquals(rating, review.getRating());
        review.setRating(newRating);
        assertEquals(newRating, review.getRating());
    }

    @Test
    void getSetText() {
        String newText = "This product is horrible";
        assertEquals(text, review.getText());
        review.setText(newText);
        assertEquals(newText, review.getText());
    }

    @Test
    void getSetProduct() {
        Product newProduct = new Product("www.example.com", "Pear", "Not orange.", ProductType.PZA, ProductChain.PPZ);
        assertEquals(product, review.getProduct());
        review.setProduct(newProduct);
        assertEquals(newProduct, review.getProduct());
    }
}