package com.sysc4806app.model;

import org.hibernate.validator.constraints.URL;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.StringJoiner;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) @NotBlank @URL
    private String link;
    @Column(nullable = false) @Size(min=3, max=20)
    private String name;
    @Column(nullable = false) @Size(min=5, max=200)
    private String description;
    @Column(nullable = false) @NotNull @URL
    private String imageLink;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) @NotNull
    private ProductType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) @NotNull
    private ProductChain chain;

    // take average rating if it exists, or 0
    @Formula(value="(SELECT coalesce(AVG(rev.rating), 0) FROM review rev WHERE rev.product_id = id)")
    private Float averageRating;

    public Product() {}

    public Product(String link, String name, String description, ProductType type, ProductChain chain, String imageLink) {
        this.link = link;
        this.name = name;
        this.description = description;
        this.type = type;
        this.chain = chain;
        this.imageLink = imageLink;
    }

    public Product(String link, String name, String description, ProductType type, ProductChain chain) {
        this(link, name, description, type, chain, "");
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getType() { return type; }

    public void setType(ProductType type) { this.type = type; }

    public ProductChain getChain() { return chain; }

    public void setChain(ProductChain chain) { this.chain = chain; }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }

        Product p = (Product) o;

        return this.getDescription().equals(p.getDescription()) && this.getLink().equals(p.getLink())
                && this.getName().equals(p.getName()) && this.getId().equals(p.getId()) && this.getImageLink().equals(p.getImageLink())
                && this.getType().equals(p.getType()) && this.getChain().equals(p.getChain());
    }

    @Override
    public String toString(){
        StringJoiner builder = new StringJoiner(", ");
        builder.add(String.format("Product: %s", id));
        builder.add(name);
        builder.add(link);
        builder.add(imageLink);
        builder.add(String.format("%s", type));
        builder.add(String.format("%s", chain));
        return builder.toString();
    }
}
