package com.cti.lifego.utils;

public class StringUtils {

    public static String getPhoneNumber(String str1, String str2){
        return str1.concat(str2);
    }

    public static String getQuantityString(int quantity){
        return (String.valueOf(quantity));
    }

    public static String convertIntToString(int value){
        return (String.valueOf(value));
    }

    public static String getOrderDate(int value){
        return ("Ordered on "+ String.valueOf(value));
    }

    public static String getDeliveryDate(int value){ return ("Delivered on "+ String.valueOf(value)); }

}
