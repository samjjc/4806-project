package com.sysc4806app.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private int rating;
    private String text;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Product product;

    public Review(){}

    public Review(int rating, String text, Product product) {
        this.rating = rating;
        this.text = text;
        this.product = product;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        }

        Review r = (Review) o;

        boolean b1 =this.getProduct().equals(r.getProduct());
        boolean b2 =this.getRating() == r.getRating();
        boolean b3 =this.getText()==r.getText();
        boolean b4 =this.getId() == r.getId();
        return  b1 && b2 && b3 &&  b4;
    }
    @Override
    public String toString(){
        return "rating: "+rating+" text :" + text + " id:"+id;
    }
}
