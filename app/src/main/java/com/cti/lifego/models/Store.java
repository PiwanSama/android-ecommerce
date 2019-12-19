/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("image")
    public int image;
    @SerializedName("rating")
    public int rating;
    @SerializedName("address")
    public String address;
    @SerializedName("location")
    public String location;
    @SerializedName("contact")
    public String contact;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
