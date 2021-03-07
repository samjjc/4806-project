package com.sysc4806app.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<Review> reviews;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<User> following;

    public User(){
        reviews = new ArrayList<>();
        following = new ArrayList<>();
    }

    public User(String name) {
        this.name = name;
        reviews = new ArrayList<>();
        following = new ArrayList<>();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User u = (User) o;

        return this.getName()==u.getName() && this.getId() == u.getId();
    }
}
