package com.sysc4806app.services;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.ProductChain;
import com.sysc4806app.model.ProductType;
import com.sysc4806app.model.Review;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
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
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(5, "This is a review.", product));
        reviews.add(new Review(3, "This is a review.", product));
        reviews.add(new Review(2, "This is a review.", product));
        reviews.add(new Review(5, "This is a review.", product));
        reviews.add(new Review(1, "This is a review.", product));
        reviews.add(new Review(0, "This is a review.", product));
        Mockito.when(reviewRepository.findByProduct(product)).thenReturn(reviews);
        Mockito.when(productRepository.findById(1)).thenReturn(product);
        double result = service.calculateRating(1);
        assertTrue(result >= 0);
        assertTrue(result <= Review.MAX_RATING);
        assertEquals(result, 16d/6d);
    }
}
