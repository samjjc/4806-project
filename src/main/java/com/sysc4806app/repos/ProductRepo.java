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

    //for average rating sorted
    List<Product> findByNameContainsIgnoreCase(String name, Pageable sort);
    List<Product> findByChainAndNameContainsIgnoreCase(ProductChain chain, String name, Pageable sort);
    List<Product> findByTypeAndNameContainsIgnoreCase(ProductType type, String name, Pageable sort);
    List<Product> findByTypeAndChainAndNameContainsIgnoreCase(ProductType type, ProductChain chain, String name, Pageable sort);

    //for average following sorted
    List<Product> findByTypeAndChainAndName(ProductType type, ProductChain chain, String name);
    List<Product> findByTypeAndName(ProductType type, String name);
    List<Product> findByChainAndName(ProductChain chain, String name);
}
