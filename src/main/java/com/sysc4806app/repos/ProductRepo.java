package com.sysc4806app.repos;

import com.sysc4806app.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {

    List<Product> findAll();
    Product findById(long id);
    List<Product> findByName(String name);

}
