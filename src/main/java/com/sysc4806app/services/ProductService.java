package com.sysc4806app.services;

import com.sysc4806app.model.Product;
import com.sysc4806app.model.Review;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;


/**
 * @version milestone 1
 *
 * A service that provides support for requests concerning products.
 */
@Service
public class ProductService {
    private final ProductRepo productRepository;
    private final ReviewRepo reviewRepository;

    /**
     * Constructs a new product service.
     * @param productRepository The product repository accessed by the service.
     * @param reviewRepository The review repository accessed by the service.
     */
    public ProductService(ProductRepo productRepository, ReviewRepo reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Returns the average rating of the reviews for a given product.
     * @param productID The id of the product, which is an existing and stored product.
     * @return The average rating of the product reviews.
     */
    public double calculateRating(long productID) {
        Product product = productRepository.findById(productID);
        List<Review> productReviews = reviewRepository.findByProduct(product);
        return Math.min(Math.max(productReviews.stream().mapToInt(Review::getRating).average().orElse(0), 0d), Review.MAX_RATING);
    }

    /**
     * Returns the list of reviews for a given product.
     * @param productID The id of the product, which is an existing and stored product.
     * @param sortField The field of the review to sort on.
     * @param direction The direction of the sort.
     * @return The reviews of the product, which are optionally sorted.
     */
    public List<Review> getProductReviews(long productID, String sortField, Sort.Direction direction) {
        Product product = productRepository.findById(productID);
        return reviewRepository.findByProduct(product, Sort.by(direction, sortField));
    }
}
