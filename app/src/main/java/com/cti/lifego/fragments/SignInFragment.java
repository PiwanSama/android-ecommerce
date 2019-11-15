package com.cti.lifego.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.cti.lifego.R;
import com.cti.lifego.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.cti.lifego.R.array.country_code_array;

public class SignInFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private TextView skip;
    private Spinner country_spinner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.signin_fragment, container, false);
        skip = view.findViewById(R.id.skip);
        country_spinner = view.findViewById(R.id.country_code);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.home_fragment);
            }
        });

        setUpCountrySpinner();

        return view;
    }

    private void setUpCountrySpinner() {
        country_spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> countryCodeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(country_code_array));
        countryCodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        country_spinner.setAdapter(countryCodeAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
