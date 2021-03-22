package com.sysc4806app.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private static String link;
    private static String name;
    private static String description;
    private static ProductType type;
    private static ProductChain chain;
    private static Product product;
    private static String imageLink;

    @BeforeAll
    static void beforeAll() {
        link = "www.link.com";
        name = "Orange";
        description = "It's orange.";
        imageLink = "www.example.com/image.jpg";
        product = new Product(link, name, description, type, chain, imageLink);
    }
    @Test
    void getSetLink() {
        String newLink = "www.example.com";
        assertEquals(link, product.getLink());
        product.setLink(newLink);
        assertEquals(newLink, product.getLink());
    }

    @Test
    void getSetName() {
        String newName = "Pear";
        assertEquals(name, product.getName());
        product.setName(newName);
        assertEquals(newName, product.getName());
    }

    @Test
    void getSetDescription() {
        String newDescription = "Not orange.";
        assertEquals(description, product.getDescription());
        product.setDescription(newDescription);
        assertEquals(newDescription, product.getDescription());
    }

    @Test
    void getSetImageLink() {
        String newLink = "www.example.com/image.jpg";
        assertEquals(imageLink, product.getImageLink());
        product.setImageLink(newLink);
        assertEquals(newLink, product.getImageLink());
    }
}