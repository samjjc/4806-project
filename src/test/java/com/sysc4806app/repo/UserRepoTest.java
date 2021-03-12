package com.sysc4806app.repo;

import com.sysc4806app.model.User;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    private User user1, user2, user3;
    private Long  id1, id2, id3;

    @BeforeAll
    public void beforeAll() {
        user1 = new User("Joe");
        user2 = new User("EIM");
        user3 = new User("Ben");

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);

        id1=user1.getId();
        id2=user2.getId();
        id3=user3.getId();

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
        Optional<User> actual = userRepo.findById(id1);
        assertEquals(user1,actual.get());
    }

    @Test
    public void findByNameTest(){
        User actual = userRepo.findByName("EIM");
        assertEquals(actual,(user2));
    }

}
