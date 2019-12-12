package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class Payment {
    @SerializedName("id")
    public int id;
    @SerializedName("amount")
    public int amount;
    @SerializedName("method")
    public int method;
    @SerializedName("date_made")
    public String order_date;
    @SerializedName("date_confirmed")
    public int delivery_date;
    @SerializedName("status")
    public String status;
    @SerializedName("order_id")
    public int product_id;

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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }
}
