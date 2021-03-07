package com.sysc4806app.model;
import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String link;
    private String name;
    private String description;

    public Product(){}

    public Product(String link, String name, String description) {
        this.link = link;
        this.name = name;
        this.description = description;
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

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }

        Product p = (Product) o;

        return this.getDescription() == p.getDescription() && this.getLink() == p.getLink()
                && this.getName()==p.getName() && this.getId() == p.getId();
    }
}
