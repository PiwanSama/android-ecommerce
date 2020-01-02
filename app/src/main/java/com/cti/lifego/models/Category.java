package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    public int id;
    @SerializedName("kin_name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("image")
    public int image;

    public Category(int id, String name, String description, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
