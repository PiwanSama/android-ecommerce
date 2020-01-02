package com.cti.lifego.models;

import androidx.lifecycle.LiveData;

public class CartItem {
    public int id;
    public String name;
    public int image;
    private int price;
    private String vendor;
    private int quantity;
    private LiveData<Product> product;

    public CartItem(LiveData<Product> product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public LiveData<Product> getProduct() {
        return product;
    }

    public void setProduct(LiveData<Product> product) {
        this.product = product;
    }
}
