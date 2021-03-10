package com.sysc4806app.model;

/**
 * @version milestone 1
 *
 * A product chain. Can be used to categorize products.
 */
public enum ProductChain implements PathVariableEnum {
    SWY("Subway", "sway", ProductChainTag.SANDWICHES),
    MCD("McDonald's", "mcdonalds", ProductChainTag.BURGERS),
    DQ("Dairy Queen", "dq", ProductChainTag.DESERTS),
    BK("Burger King", "burgerking", ProductChainTag.BURGERS),
    AW("A & W", "aw", ProductChainTag.BURGERS),
    FG("Five Guys", "fiveguys", ProductChainTag.BURGERS),
    PPZ("Pizza Pizza", "pizzapizza", ProductChainTag.PIZZA),
    DOM("Dominos", "dominos", ProductChainTag.PIZZA),
    QNO("Quiznos", "quiznos", ProductChainTag.SANDWICHES),
    TIM("Tim Horton's", "timmies", ProductChainTag.COFFEE, ProductChainTag.DESERTS, ProductChainTag.BREAKFAST),
    KFC("Kentucky Fried Chicken", "kfc", ProductChainTag.FRIEDCHICKEN),
    MAC("Supermac's", "supermacs", ProductChainTag.FRIEDCHICKEN),
    CPT("Captain Submarine", "captainsub", ProductChainTag.SANDWICHES),
    HRV("Harvey's", "harveys", ProductChainTag.BURGERS),
    PIT("Pita Pit", "pitapit", ProductChainTag.SANDWICHES),
    MRS("Mr. Sub", "mrsub", ProductChainTag.SANDWICHES),
    NYF("New York Fries", "nyfries", ProductChainTag.BURGERS),
    ARB("Arby's", "arbys", ProductChainTag.BURGERS),
    BR("Baskin-Robbins", "baskin", ProductChainTag.DESERTS),
    TB("Taco Bell", "tacobell", ProductChainTag.SANDWICHES),
    WEN("Wendy's", "wendys", ProductChainTag.BURGERS),
    CAE("Little Caesars", "lilcaesars", ProductChainTag.PIZZA),
    DD("Dunkin' Donuts", "dunkin", ProductChainTag.DESERTS),
    POP("Popeyes", "popeyes", ProductChainTag.FRIEDCHICKEN),
    STR("Starbucks", "starbucks", ProductChainTag.COFFEE),
    SCC("Second Cup", "secondcup", ProductChainTag.COFFEE),
    NAN("Nando's", "nandos", ProductChainTag.FRIEDCHICKEN),
    FAT("Fatburger", "fatburger", ProductChainTag.BURGERS),
    HUT("Pizza Hut", "pizzahut", ProductChainTag.PIZZA);

    private final String text, urlSegment;
    private final ProductChainTag[] categoryTags;

    /**
     * Creates a new product chain.
     * @param text The display text associated with the product chain.
     * @param urlSegment The url segment associated with the product chain.
     * @param tags The product chain tags that categorize the product chain.
     */
    ProductChain(String text, String urlSegment, ProductChainTag... tags) {
        this.text = text;
        this.urlSegment = urlSegment;
        this.categoryTags = tags;
    }

    /**
     * Gets the display text of the product chain.
     * @return The display text.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the url segment used to map from request to product chain.
     * @return The url segment.
     */
    @Override
    public String getUrlSegment() {
        return urlSegment;
    }

    /**
     * Gets the tags associated with a product chain.
     * @return The product chain tags.
     */
    public ProductChainTag[] getCategoryTags() {
        return categoryTags;
    }
}
