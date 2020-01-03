package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    public int id;
    @SerializedName("total_price")
    public int total_price;
    @SerializedName("order_date")
    public String order_date;
    @SerializedName("delivery_date")
    public int delivery_date;
    @SerializedName("delivery_address")
    public String delivery_address;
    @SerializedName("status")
    public String status;
    @SerializedName("product")
    public Product product;
    @SerializedName("store")
    public Store store;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(int delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
