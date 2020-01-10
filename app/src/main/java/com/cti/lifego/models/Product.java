package com.cti.lifego.models;

import com.cti.lifego.intefaces.Saleable;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Saleable, Serializable {

    private int id;
    private String name;
    private int image;
    private BigDecimal price;
    private String vendor;
    private String description;
    private int rating;

    public Product() {
    }

    public Product(int id, String name, int image, BigDecimal price, String description, int rating) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.vendor = vendor;
        this.description = description;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
