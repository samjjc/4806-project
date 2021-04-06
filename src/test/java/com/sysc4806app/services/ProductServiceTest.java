package com.sysc4806app.services;

import com.sysc4806app.model.*;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepo productRepository;

    @MockBean
    private ReviewRepo reviewRepository;

    @Autowired
    private ProductService service;

    @Test
    public void TestCalculateRating() {
        Product product = new Product("https://sysc4806app.herokuapp.com/", "Website", "The website.", ProductType.ICE, ProductChain.DQ);
        User user = new User("tester", "pass1");
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(5, "This is a review.", product, user));
        reviews.add(new Review(3, "This is a review.", product, user));
        reviews.add(new Review(2, "This is a review.", product, user));
        reviews.add(new Review(5, "This is a review.", product, user));
        reviews.add(new Review(1, "This is a review.", product, user));
        reviews.add(new Review(0, "This is a review.", product, user));
        Mockito.when(reviewRepository.findByProduct(product)).thenReturn(reviews);
        Mockito.when(productRepository.findById(1)).thenReturn(product);
        double result = service.calculateRating(1);
        assertTrue(result >= 0);
        assertTrue(result <= Review.MAX_RATING);
        assertEquals(result, 16d/6d);
    }

    @Test
    public void TestCalculateFollowRating() {
        Product product = new Product("https://sysc4806app.herokuapp.com/", "Website", "The website.", ProductType.ICE, ProductChain.DQ);
        User user1 = new User("tester", "pass1");
        User user2 = new User("tester2", "pass2");
        User user3 = new User("tester3", "pass3");
        user1.followUser(user2);
        user1.followUser(user3);
        Collection<User> following = user1.getFollowing();

        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(3, "This is a review.", product, user2));
        reviews.add(new Review(2, "This is a review.", product, user3));
        reviews.add(new Review(5, "This is a review.", product, user2));
        reviews.add(new Review(0, "This is a review.", product, user3));
        Mockito.when(reviewRepository.findByProductAndUserIn(product,following)).thenReturn(reviews);
        Mockito.when(productRepository.findById(1)).thenReturn(product);
        double result = service.calculateFollowingRating(1,following);
        assertTrue(result >= 0);
        assertTrue(result <= Review.MAX_RATING);
        assertEquals(result, 10d/4d);
    }

    @Test
    public void TestGetNewestProducts() {
        Product product = new Product("https://sysc4806app.herokuapp.com/", "Website", "The website.", ProductType.ICE, ProductChain.DQ);
        Product product2 = new Product("https://sysc4806app.herokuapp.com/", "Website1", "The website.", ProductType.ICE, ProductChain.DQ);
        Product product3 = new Product("https://sysc4806app.herokuapp.com/", "Website3", "The website.", ProductType.ICE, ProductChain.DQ);
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        products.add(product3);
        Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());
        Mockito.when(productRepository.findAll(pageable)).thenReturn(new PageImpl<>(products));
        Collection<Product> result = service.getNewestProducts(3);
        assertEquals(result, products);
    }
}
