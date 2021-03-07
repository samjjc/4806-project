package com.sysc4806app.repo;

import com.sysc4806app.model.Product;
import com.sysc4806app.repos.ProductRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    private  Product prod1, prod2, prod3;

    @BeforeAll
    public void beforeAll() {
        productRepo.deleteAll();
        prod1 = new Product("www.joeiscool.com", "JOMJOMS","you already know.");
        prod2 = new Product("www.eimdem.com", "tacos","what what.");
        prod3 = new Product("www.soup.com", "tacos","yum tum");

        productRepo.save(prod1);
        productRepo.save(prod2);
        productRepo.save(prod3);

    }

    @Test
    public void findAllTest(){
        List<Product> actual = productRepo.findAll();
        assertTrue(actual.contains(prod1));
        assertTrue(actual.contains(prod2));
        assertTrue(actual.contains(prod3));

    }

    @Test
    public void findByIdTest(){
        Product actual = productRepo.findById(1);
        assertEquals(prod1,actual);
    }

    @Test
    public void findByNameTest(){
        List<Product> actual = productRepo.findByName("tacos");
        assertTrue(actual.contains(prod2));
        assertTrue(actual.contains(prod3));
    }
}
