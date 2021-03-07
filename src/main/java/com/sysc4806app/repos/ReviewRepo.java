package com.sysc4806app.repos;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepo extends CrudRepository<Review, Long> {

    List<Review> findAll();
    Review findById(long id);
    List<Review> findByRating(int rating);
    List<Review> findByProduct(Product product);

}
