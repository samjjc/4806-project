package com.sysc4806app.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ChainCategoryResponseTest {
    private static String link;
    private static String name;
    private static String description;
    private static ProductType type;
    private static ProductChain chain;
    private static Product product;
    private static String imageLink;
    private static ProductChainTag tag;
    private static ChainCategoryResponse ccresponse;
    private static List<Pair<ProductChain, String>> chainAndImages;

    @BeforeAll
    static void beforeAll() {
        tag = ProductChainTag.COFFEE;
        chainAndImages = new ArrayList<>();
        chainAndImages.add(Pair.of(ProductChain.HRV, "https://sysc4806app.herokuapp.com/"));
        chainAndImages.add(Pair.of(ProductChain.DQ, "https://sysc4806app.herokuapp.com/chains"));
        chainAndImages.add(Pair.of(ProductChain.SWY, "https://sysc4806app.herokuapp.com/login"));
        ccresponse = new ChainCategoryResponse(tag, chainAndImages);
    }

    @Test
    void getSetTag() {
        assertEquals(tag, ccresponse.getTag());
        ProductChainTag newTag = ProductChainTag.PIZZA;
        assertNotEquals(tag, newTag);
        ccresponse.setTag(newTag);
        assertEquals(newTag, ccresponse.getTag());
    }

    @Test
    void getSetChainAndImage() {
        assertEquals(chainAndImages, ccresponse.getChainAndImage());
        List<Pair<ProductChain, String>> newChainAndImages = new ArrayList<>();
        newChainAndImages.add(Pair.of(ProductChain.AW, "https://sysc4806app.herokuapp.com/"));
        newChainAndImages.add(Pair.of(ProductChain.DQ, "https://sysc4806app.herokuapp.com/chains"));
        newChainAndImages.add(Pair.of(ProductChain.BK, "https://sysc4806app.herokuapp.com/productlist"));
        assertNotEquals(chainAndImages, newChainAndImages);
        ccresponse.setChainAndImage(newChainAndImages);
        assertEquals(ccresponse.getChainAndImage(), newChainAndImages);
    }

    @Test
    void addChainAndImage() {
        Pair<ProductChain, String> added = Pair.of(ProductChain.CAE, "https://sysc4806app.herokuapp.com/error");
        assertFalse(ccresponse.getChainAndImage().contains(added));
        ccresponse.addChainAndImage(ProductChain.CAE, "https://sysc4806app.herokuapp.com/error");
        assertTrue(ccresponse.getChainAndImage().contains(added));
    }
}
