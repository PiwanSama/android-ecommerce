package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public int id;
    @SerializedName("surname")
    public String surname;
    @SerializedName("given_name")
    public String givenName;
    @SerializedName("email")
    public String email;
    @SerializedName("phone_number")
    public String phone;
    @SerializedName("date_of_birth")
    public String dob;
    @SerializedName("gender")
    public String gender;
    @SerializedName("opt_in")
    public String opt_in;
    @SerializedName("password")
    public String password;
    @SerializedName("next_of_kin_name")
    public String next_of_kin_name;
    @SerializedName("kin_phone_number")
    public String kin_phone_number;
    @SerializedName("relationship_type")
    public int relationship_type;

    @SerializedName("image")
    public String image;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOpt_in() {
        return opt_in;
    }

    public void setOpt_in(String opt_in) {
        this.opt_in = opt_in;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNext_of_kin_name() {
        return next_of_kin_name;
    }

    public void setNext_of_kin_name(String next_of_kin_name) {
        this.next_of_kin_name = next_of_kin_name;
    }

    public String getKin_phone_number() {
        return kin_phone_number;
    }

    public void setKin_phone_number(String kin_phone_number) {
        this.kin_phone_number = kin_phone_number;
    }

    public int getRelationship_type() {
        return relationship_type;
    }

    public void setRelationship_type(int relationship_type) {
        this.relationship_type = relationship_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}