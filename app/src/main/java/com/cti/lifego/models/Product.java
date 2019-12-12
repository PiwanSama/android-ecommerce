package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public int image;
    @SerializedName("price")
    public String price;
    @SerializedName("vendor")
    public String vendor;
    @SerializedName("quantity")
    public int quantity;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
