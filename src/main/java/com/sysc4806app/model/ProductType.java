package com.sysc4806app.model;

/**
 * @version milestone 1
 *
 * A type of fast food product. Can be used to categorize a product.
 */
public enum ProductType implements PathVariableEnum {
    PZA("Pizza", "pizza"),
    BGR("Burgers", "burgers"),
    ICE("Ice Cream", "icecream"),
    SDW("Sandwiches", "sandwich"),
    CFE("Coffee", "coffee"),
    FC("Fried Chicken" ,"fc"),
    FRI("Fries", "fries");

    private final String text, urlSegment;

    /**
     * Creates a new product type.
     * @param text The display text associated with the product type.
     * @param urlSegment The url segment or request parameter associated with the product type.
     */
    ProductType(String text, String urlSegment) {
        this.text = text;
        this.urlSegment = urlSegment;
    }

    /**
     * Gets the display text of the product type.
     * @return The display text.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the url segment used to map from request to product type.
     * @return The url segment.
     */
    @Override
    public String getUrlSegment() {
        return urlSegment;
    }
}