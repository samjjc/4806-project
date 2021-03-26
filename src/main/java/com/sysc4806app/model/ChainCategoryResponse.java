package com.sysc4806app.model;

import org.springframework.data.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class ChainCategoryResponse {
    private ProductChainTag tag;

    private List<Pair<ProductChain, String>> chainAndImage;

    public ChainCategoryResponse(ProductChainTag tag, List<Pair<ProductChain, String>> chainAndImage) {
        this.tag = tag;
        this.chainAndImage = chainAndImage;
    }

    public ChainCategoryResponse(ProductChainTag tag) {
        this(tag, new ArrayList<>());
    }

    public ProductChainTag getTag() {
        return tag;
    }

    public void setTag(ProductChainTag tag) {
        this.tag = tag;
    }

    public List<Pair<ProductChain, String>> getChainAndImage() {
        return chainAndImage;
    }

    public void setChainAndImage(List<Pair<ProductChain, String>> chainAndImage) {
        this.chainAndImage = chainAndImage;
    }

    public void addChainAndImage(ProductChain chain, String chainImage) {
        this.chainAndImage.add(Pair.of(chain, chainImage));
    }

}
