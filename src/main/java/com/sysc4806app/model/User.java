package com.sysc4806app.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = User.TABLE_NAME) // "user" is a key word in postgres
public class User {

    public static final String TABLE_NAME="app_user";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private Collection<Review> reviews;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Collection<User> following;

    public User(){
        reviews = new ArrayList<>();
        following = new ArrayList<>();
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        reviews = new ArrayList<>();
        following = new ArrayList<>();
    }

//    public User(String name) {
//        this.name = name;
//        this.password = "pass1";
//        reviews = new ArrayList<>();
//        following = new ArrayList<>();
//    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void unfollowUser(User user) {
        following.remove(user);
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

        return this.getName().equals(u.getName()) && this.getId().equals(u.getId());
    }
}
