/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("id")
    public int id;
    @SerializedName("category_id")
    public int category_id;
    public String name;
    @SerializedName("image")
    public int image;
    @SerializedName("rating")
    public int rating;
    @SerializedName("location")
    public String location;
    @SerializedName("contact")
    public String contact;

    public Store(int id, int category_id, String name, int image, int rating, String location, String contact) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.location = location;
        this.contact = contact;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
