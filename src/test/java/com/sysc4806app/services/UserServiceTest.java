package com.sysc4806app.services;

import com.sysc4806app.model.*;
import com.sysc4806app.repos.UserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepo userRepository;

    @Autowired
    private UserService service;

    User user1, user2, user3, user4;
    Product product1, product2, product3;

    @BeforeAll
    public void beforeAll() {
        user1 = new User("user1","1");
        user2 = new User("user2","2");
        user3 = new User("user3","3");
        user4 = new User("user4","4");
        product1 = new Product("https://sysc4806app.herokuapp.com/", "Website", "The website.", ProductType.ICE, ProductChain.DQ);
        product2 = new Product("https://sysc4806app.herokuapp.com/", "Website1", "The website.", ProductType.ICE, ProductChain.DQ);
        product3 = new Product("https://sysc4806app.herokuapp.com/", "Website2", "The website.", ProductType.ICE, ProductChain.DQ);
        user1.addReview(new Review(0,"",product1,user1));
        user1.addReview(new Review(2,"",product2,user1));
        user1.addReview(new Review(2,"",product3,user1));
        user2.addReview(new Review(3,"",product1,user2));
        user2.addReview(new Review(4,"",product2,user2));
        user3.addReview(new Review(2,"",product1,user3));
    }

    @Test
    public void TestEuclideanDistance() {
        assertEquals(service.getEuclideanDistance(user1, user1), 0);
        assertEquals(service.getEuclideanDistance(user3, user3), 0);
        assertFalse(service.getEuclideanDistance(user1, user2) < service.getEuclideanDistance(user1, user3));
        assertTrue(service.getEuclideanDistance(user2, user3) < service.getEuclideanDistance(user1, user2));
        assertTrue(service.getEuclideanDistance(user1, user2) < service.getEuclideanDistance(user1, user4));
        assertTrue(service.getEuclideanDistance(user1, user3) < service.getEuclideanDistance(user1, user4));
        assertTrue(service.getEuclideanDistance(user2, user3) < service.getEuclideanDistance(user1, user4));
        assertTrue(service.getEuclideanDistance(user1, user3) < service.getEuclideanDistance(user1, user4));
    }

    @Test
    public void TestJaccardDistance() {
        assertEquals(service.getJaccardDistance(user1, user1), 0);
        assertEquals(service.getJaccardDistance(user3, user4), 1);
        assertEquals(service.getJaccardDistance(user3, user2), 0.5);
        assertTrue(service.getJaccardDistance(user1, user2) < service.getJaccardDistance(user1, user3));
        assertTrue(service.getJaccardDistance(user1, user2) < service.getJaccardDistance(user1, user4));
        assertTrue(service.getJaccardDistance(user1, user3) < service.getJaccardDistance(user1, user4));
        assertTrue(service.getJaccardDistance(user2, user3) < service.getJaccardDistance(user1, user4));
        assertTrue(service.getJaccardDistance(user1, user3) < service.getJaccardDistance(user1, user4));
    }

    @Test
    public void TestGetMostSimilarUsers() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user3);
        users.add(user4);
        users.add(user2);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(userRepository.findByName("user1")).thenReturn(user1);
        Collection<User> result = service.getMostSimilarUsers("user1", 4);
        Iterator<User> itr = result.iterator();
        assertEquals(itr.next(), user2);
        assertEquals(itr.next(), user3);
        assertEquals(itr.next(), user4);
        assertFalse(itr.hasNext());
    }

    @Test
    public void TestGetDegreeOfSeparationNumber() {
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user3);
        users.add(user4);
        users.add(user2);
        User user5 =new User("user5","123");

        user1.followUser(user2);
        user1.followUser(user4);
        user2.followUser(user1); //test loops
        user2.followUser(user3);
        user2.followUser(user4);
        user3.followUser(user2);
        //self dos
        int result = service.getDegreeOfSeparationNumber(user1, user1);
        assertEquals(0,result);
        //normal dos
        result = service.getDegreeOfSeparationNumber(user1, user2);
        assertEquals(1,result);
        result = service.getDegreeOfSeparationNumber(user1, user3);
        assertEquals(2,result);
        //unlinked user
        result = service.getDegreeOfSeparationNumber(user1, user5);
        assertEquals(-1,result);
        //user with no following
        result = service.getDegreeOfSeparationNumber(user4, user2);
        assertEquals(-1,result);

    }
}
