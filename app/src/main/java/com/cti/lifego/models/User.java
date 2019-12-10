package com.cti.lifego.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("surname")
    public String surname;
    @SerializedName("givenName")
    public String givenName;
    @SerializedName("email")
    public String email;
    @SerializedName("phone")
    public String phone;
    @SerializedName("dob")
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
    public String relationship_type;
    
}