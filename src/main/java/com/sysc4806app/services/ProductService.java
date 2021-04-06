package com.sysc4806app.services;

import com.sysc4806app.model.*;
import com.sysc4806app.repos.ProductRepo;
import com.sysc4806app.repos.ReviewRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


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
     * Returns the average following rating of the reviews by collection of users for a given product.
     * @param productID The id of the product, which is an existing and stored product.
     * @param following The list of users the reviews are created by.
     * @return The average following rating of the product reviews.
     */
    public double calculateFollowingRating(long productID, Collection<User> following) {
        Product product = productRepository.findById(productID);
        List<Review> productReviews = reviewRepository.findByProductAndUserIn(product,following);
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

    public String getRandomImageForChain(ProductChain chain) {
        List<Product> products = productRepository.findByChain(chain);
        Product[] productsWithImage = products.stream().filter(x -> !x.getImageLink().equals("")).toArray(Product[]::new);
        if (productsWithImage.length < 1) return "";
        return productsWithImage[new Random().nextInt(productsWithImage.length)].getImageLink();
    }

    public List<ChainCategoryResponse> getChainImageByCategory() {
        List<ChainCategoryResponse> results = new ArrayList<ChainCategoryResponse>();
        for (ProductChainTag category : ProductChainTag.values()) {
            ChainCategoryResponse categoryResponse = new ChainCategoryResponse(category);
            for (ProductChain chain : ProductChain.values()) {
                if (Arrays.asList(chain.getCategoryTags()).contains(category)) {
                    categoryResponse.addChainAndImage(chain, getRandomImageForChain(chain));
                }
            }
            results.add(categoryResponse);
        }
        return results;
    }

    public Collection<Product> getNewestProducts(int numProducts) {
        return productRepository.findAll(PageRequest.of(0, numProducts, Sort.by("id").descending())).getContent();
    }
}
