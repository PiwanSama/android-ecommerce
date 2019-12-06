package com.cti.lifego.models;

import android.util.Patterns;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("surname")
    private String surname;
    @SerializedName("givenName")
    private String givenName;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("dob")
    private String dob;
    @SerializedName("gender")
    private String gender;
    @SerializedName("opt_in")
    private String opt_in;
    @SerializedName("password")
    private String password;
    @SerializedName("nok_name")
    private String nok_name;
    @SerializedName("nok_phone")
    private String nok_phone;
    @SerializedName("nok_relationship")
    private String nok_relationship;

    public User(String surname, String givenName, String email, String phone, String dob, String gender, String opt_in, String password, String nok_name, String nok_phone, String nok_relationship) {
        this.surname = surname;
        this.givenName = givenName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.opt_in = opt_in;
        this.password = password;
        this.nok_name = nok_name;
        this.nok_phone = nok_phone;
        this.nok_relationship = nok_relationship;
    }

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

    public String getNok_name() {
        return nok_name;
    }

    public void setNok_name(String nok_name) {
        this.nok_name = nok_name;
    }

    public String getNok_phone() {
        return nok_phone;
    }

    public void setNok_phone(String nok_phone) {
        this.nok_phone = nok_phone;
    }

    public String getNok_relationship() {
        return nok_relationship;
    }

    public void setNok_relationship(String nok_relationship) {
        this.nok_relationship = nok_relationship;
    }

    public void setUser(){
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
    }

    public boolean isPasswordLengthGreaterThan5() {
        return getPassword().length() > 8;
    }

}