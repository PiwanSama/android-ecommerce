/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.content;

import com.cti.lifego.R;
import com.cti.lifego.models.PaymentOption;

import java.util.HashMap;

public class PaymentOptions {

    public PaymentOption [] PAYMENT_OPTIONS = {MTNMOMO, AIRTELMONEY};

    public HashMap<String, PaymentOption> PAYMENT_OPTIONS_MAP = new HashMap<>();

    public PaymentOptions(){
        for (PaymentOption paymentOption : PAYMENT_OPTIONS){
            PAYMENT_OPTIONS_MAP.put(String.valueOf(paymentOption.getId()), paymentOption);
        }
    }

    public static final PaymentOption MTNMOMO = new PaymentOption(1,"MTN Momo", R.drawable.ic_mtnmomo);

    public static final PaymentOption AIRTELMONEY = new PaymentOption(1,"Airtel Money", R.drawable.airtelmoney);
}
