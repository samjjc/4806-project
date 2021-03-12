package com.sysc4806app.repo;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.Review;
import com.sysc4806app.model.User;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    private User user1, user2, user3;

    @BeforeAll
    public void beforeAll() {

        user1 = new User("Joe");
        user2 = new User("EIM");
        user3 = new User("Ben");

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);

    }

    @Test
    public void findAllTest(){
        List<User> actual = userRepo.findAll();
        assertTrue(actual.contains(user1));
        assertTrue(actual.contains(user2));
        assertTrue(actual.contains(user3));
    }

    @Test
    public void findByIdTest(){
        User actual = userRepo.findById(1);
        assertEquals(user1,actual);
    }

    @Test
    public void findByNameTest(){
        List<User> actual = userRepo.findByName("EIM");
        assertTrue(actual.contains(user2));
    }

}
