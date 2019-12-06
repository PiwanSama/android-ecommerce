/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerFragmentListener datePickerFragmentListener;
    private Calendar c;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        c = Calendar.getInstance();
        int dd = c.get(Calendar.DAY_OF_MONTH);
        int mm = c.get(Calendar.MONTH);
        int yy = c.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),this,dd,mm,yy);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.getDatePicker().setMinDate(1577923200);
        return datePickerDialog;
    }

    interface DatePickerFragmentListener {
        void onDateSet(DatePicker view, int day, int month, int year);
    }

    public void onDateSet(DatePicker view, int day, int month, int year) {
        datePickerFragmentListener.onDateSet(view, year, month, day);

    }
}

