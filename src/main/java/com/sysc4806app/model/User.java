package com.sysc4806app.model;


import java.util.ArrayList;
import java.util.Collection;

public class User {

    private String name;
    private Collection<Review> reviews;
    private Collection<User> following;

    public User(String name) {
        this.name = name;
        reviews = new ArrayList<>();
        following = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public void followUser(User user) {
        following.add(user);
    }

    public Collection<User> getFollowing() {
        return following;
    }

    public void setFollowing(Collection<User> following) {
        this.following = following;
    }
}
