package com.sysc4806app.repo;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.Review;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ReviewRepoTest {

    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private ProductRepo productRepo;

    private Review review1, review2, review3;
    private Product prod1, prod2;


    @BeforeAll
    public void beforeAll() {
        prod1 = new Product("www.joeiscool.com", "JOMJOMS","you already know.");
        prod2 = new Product("www.soup.com", "tacos","yum tum");

        review1 = new Review(5,"good",prod1);
        review2 = new Review(3, "avg",prod1);
        review3 = new Review(3, "avg",prod2);
        productRepo.save(prod1);
        productRepo.save(prod2);
        reviewRepo.save(review1);
        reviewRepo.save(review2);
        reviewRepo.save(review3);

    }

    @Test
    public void findByProductTest(){
        Product test =productRepo.findById(1);
        List<Review> actual = reviewRepo.findByProduct(test);
        assertTrue(actual.contains(review1));
        assertTrue(actual.contains(review2));
    }

    @Test
    public void findAllTest(){
        List<Review> actual = reviewRepo.findAll();
        assertTrue(actual.contains(review1));
        assertTrue(actual.contains(review2));
        assertTrue(actual.contains(review3));
    }

    @Test
    public void findByIdTest(){
        Review actual = reviewRepo.findById(1);
        assertEquals(review1,actual);
    }

    @Test
    public void findByRatingTest(){
        List<Review> actual = reviewRepo.findByRating(3);
        assertTrue(actual.contains(review2));
        assertTrue(actual.contains(review3));
    }
}
