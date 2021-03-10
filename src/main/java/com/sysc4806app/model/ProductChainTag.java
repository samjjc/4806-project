package com.sysc4806app.model;

/**
 * @version milestone 1
 *
 * A qualifier tag on a product chain. Can be used to categorize and tag a product chain.
 */
public enum ProductChainTag implements PathVariableEnum {
    PIZZA("Pizza", "pizza"),
    BURGERS("Burgers and Fries", "burgers"),
    DESERTS("Deserts", "deserts"),
    SANDWICHES("Sandwiches", "sandwiches"),
    COFFEE("Coffee Shops", "coffee"),
    FRIEDCHICKEN("Fried Chicken", "friedchicken"),
    BREAKFAST("Breakfast Foods", "breakfast");

    private final String text, urlSegment;

    /**
     * Creates a new product chain tag.
     * @param text The display text associated with the product chain tag.
     * @param urlSegment The url segment associated with the product chain tag.
     */
    ProductChainTag(String text, String urlSegment) {
        this.text = text;
        this.urlSegment = urlSegment;
    }

    /**
     * Gets the display text of the product chain tag.
     * @return The display text.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the url segment used to map from request to product chain tag.
     * @return The url segment.
     */
    @Override
    public String getUrlSegment() {
        return urlSegment;
    }
}
