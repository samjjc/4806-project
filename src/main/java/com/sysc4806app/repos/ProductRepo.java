package com.sysc4806app.repos;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.ProductChain;
import com.sysc4806app.model.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepo extends PagingAndSortingRepository<Product, Long> {

    List<Product> findAll();
    Product findById(long id);
    List<Product> findByName(String name);
    List<Product> findByChain(ProductChain chain);
    List<Product> findByChain(ProductChain chain, Pageable sort);
    List<Product> findByType(ProductType type);
    List<Product> findByType(ProductType type, Pageable sort);
    List<Product> findByTypeAndChain(ProductType type, ProductChain chain, Pageable sort);
}
