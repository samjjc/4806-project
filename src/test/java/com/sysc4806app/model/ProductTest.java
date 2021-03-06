package com.sysc4806app.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private static String link;
    private static String name;
    private static String description;
    private static Product product;

    @BeforeAll
    static void beforeAll() {
        link = "www.link.com";
        name = "Orange";
        description = "It's orange.";
        product = new Product(link, name, description);
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
}