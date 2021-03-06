package com.sysc4806app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private String name;
    private User guy;

    @BeforeEach
    public void setUp() {
        name = "Bob";
        guy = new User(name);
    }

    @Test
    void getName() {
        assertEquals(name, guy.getName());
    }

    @Test
    void setName() {
        String newName = "Rob";
        guy.setName(newName);
        assertEquals(newName, guy.getName());
    }

    @Test
    void addReviews() {
        assertTrue(guy.getReviews().isEmpty());
        Product product = new Product("www.link.com", "Orange", "It's orange.");
        Review review = new Review(5, "This product is very good", product);
        guy.addReview(review);
        assertFalse(guy.getReviews().isEmpty());
        assertTrue(guy.getReviews().contains(review));
    }

    @Test
    void getSetReviews() {
        ArrayList<Review> newReviews = new ArrayList<>();
        assertNotSame(newReviews, guy.getReviews());
        guy.setReviews(newReviews);
        assertSame(newReviews, guy.getReviews());
    }

    @Test
    void addFollowers() {
        assertTrue(guy.getFollowing().isEmpty());
        User user = new User("Rob");
        guy.followUser(user);
        assertFalse(guy.getFollowing().isEmpty());
        assertTrue(guy.getFollowing().contains(user));
    }

    @Test
    void getSetFollowing() {
        ArrayList<User> newFollowing = new ArrayList<>();
        assertNotSame(newFollowing, guy.getFollowing());
        guy.setFollowing(newFollowing);
        assertSame(newFollowing, guy.getFollowing());
    }
}