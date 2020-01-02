package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    public int id;
    @SerializedName("kin_name")
    public String name;
    @SerializedName("image")
    public int image;
    @SerializedName("price")
    private int price;
    @SerializedName("vendor")
    private String vendor;
    @SerializedName("description")
    public String description;
    @SerializedName("rating")
    public int rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
