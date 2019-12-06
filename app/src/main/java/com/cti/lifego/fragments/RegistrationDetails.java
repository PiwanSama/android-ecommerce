package com.cti.lifego.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.viewmodels.UserRegistrationViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Pattern;

import static com.cti.lifego.R.array.country_code_array;

public class RegistrationDetails extends Fragment implements AdapterView.OnItemSelectedListener{

    private Spinner phone_spinner, phone_spinner2;
    Pattern pattern = Patterns.EMAIL_ADDRESS;
    UserRegistrationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.registration_details, container, false);

        viewModel = new ViewModelProvider(this).get(UserRegistrationViewModel.class);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phone_spinner = view.findViewById(R.id.country_code);
        phone_spinner2 = view.findViewById(R.id.nok_country_code);
        setUpPhoneSpinner();

        MaterialButton datePicker = view.findViewById(R.id.dateSelect);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.datepicker);
            }
        });

        MaterialButton buttonRegister = view.findViewById(R.id.register_button);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.postUser();
            }
        });
    }

    private void setUpPhoneSpinner() {
        phone_spinner.setOnItemSelectedListener(this);
        phone_spinner2.setOnItemSelectedListener(this);
        ArrayAdapter<String> codeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(country_code_array));
        codeAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        phone_spinner.setAdapter(codeAdapter);
        phone_spinner2.setAdapter(codeAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
        {
            int id = parent.getId();
            if (id == R.id.spinner_region){
                String region = parent.getItemAtPosition(position).toString();
                switch (region){

                }
            }

        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
