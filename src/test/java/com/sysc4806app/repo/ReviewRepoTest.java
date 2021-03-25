package com.sysc4806app.repo;

import com.sysc4806app.model.*;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Autowired
    private UserRepo userRepo;

    private Review review1, review2, review3;
    private Product prod1, prod2;
    private User user;


    @BeforeAll
    public void beforeAll() {
        productRepo.deleteAll();
        user = userRepo.save(new User("tester", "pass1"));
        prod1 = productRepo.save(new Product("http://www.joeiscool.com", "JOMJOMS","you already know.", ProductType.CFE, ProductChain.TIM));
        prod2 = productRepo.save(new Product("http://www.soup.com", "tacos", "yum tum", ProductType.BGR, ProductChain.AW));
        review1 = reviewRepo.save(new Review(5,"good",prod1, user));
        review2 = reviewRepo.save(new Review(3, "avg",prod1, user));
        review3 = reviewRepo.save(new Review(3, "avg",prod2, user));
    }

    @Test
    public void findByProductTest(){
        Product test =productRepo.findById(1);
        List<Review> reviews = reviewRepo.findAll();
        List<Review> actual = reviewRepo.findByProduct(test);
        for(Review r : reviews){
            if (r.getProduct().equals(test)){
                assertTrue(actual.contains(r));
            }
        }
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

    @AfterAll()
    public void afterAll(){
        reviewRepo.deleteAll();
        productRepo.deleteAll();
        userRepo.deleteAll();
    }
}
