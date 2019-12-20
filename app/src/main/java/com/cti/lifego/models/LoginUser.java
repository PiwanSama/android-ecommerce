/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class LoginUser {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public LoginUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
