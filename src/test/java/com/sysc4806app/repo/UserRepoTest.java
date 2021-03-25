package com.sysc4806app.repo;

import com.sysc4806app.model.User;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.*;
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
        user1 = userRepo.save(new User("Joe"));
        user2 = userRepo.save(new User("EIM"));
        user3 = userRepo.save(new User("Ben"));
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
        User actual = userRepo.findById(user1.getId().longValue());
        assertEquals(user1,actual);
    }

    @Test
    public void findByNameTest(){
        User actual = userRepo.findByName("EIM");
        assertEquals(user2, actual);
    }

    @AfterAll()
    public void afterAll(){
        userRepo.deleteAll();
    }
}
