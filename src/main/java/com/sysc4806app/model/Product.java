package com.sysc4806app.model;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String link;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductChain chain;

    public Product() {}

    public Product(String link, String name, String description, ProductType type, ProductChain chain) {
        this.link = link;
        this.name = name;
        this.description = description;
        this.type = type;
        this.chain = chain;
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
                && this.getName().equals(p.getName()) && this.getId().equals(p.getId())
                && this.getType().equals(p.getType()) && this.getChain().equals(p.getChain());
    }

    @Override
    public String toString(){
        StringJoiner builder = new StringJoiner(", ");
        builder.add(String.format("Product: %s", id));
        builder.add(name);
        builder.add(link);
        builder.add(String.format("%s", type));
        builder.add(String.format("%s", chain));
        return builder.toString();
    }
}
