/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.databinding.FragmentKinDetailsBinding;
import com.cti.lifego.utils.StringUtils;
import com.cti.lifego.viewmodels.RegistrationViewModel;

import static com.cti.lifego.R.array.country_code_array;
import static com.cti.lifego.R.array.relationship_array;
import static com.cti.lifego.viewmodels.RegistrationViewModel.RegistrationState.REGISTRATION_COMPLETE;

public class KinDetailsFragment extends BaseFragment{

    private RegistrationViewModel registrationViewModel;
    private FragmentKinDetailsBinding binding;
    private Spinner relationship_spinner, country_code_spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kin_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(registrationViewModel);

        setUpSpinner();

        NavController navController = Navigation.findNavController(view);

        binding.registerButton.setOnClickListener(v -> {
            // When the next button is clicked, collect the current values from the two edit texts
            // and pass to the ViewModel to store.
            String kin_phone = StringUtils.concatStrings(binding.kinCountryCode.getSelectedItem().toString(), binding.kinPhoneNumber.getText().toString());
            String relationship_type = binding.relationshipTypeSpinner.getSelectedItem().toString();

            if (TextUtils.isEmpty(relationship_type)){
                Toast.makeText(getContext(), "Select your relationship type", Toast.LENGTH_SHORT).show();
            }
            else{
                registrationViewModel.collectKinData(kin_phone, getRelationshipType(relationship_type));
            }
        });

        // RegistrationViewModel updates the registrationState to
        // COLLECT_USER_PASSWORD when ready to move to the choose username and
        // password screen.
        registrationViewModel.getRegistrationState().observe(getViewLifecycleOwner(), state -> {
            if (state == REGISTRATION_COMPLETE) {
                navController.navigate(R.id.kinDetailsFragment_to_homeFragment);
            }
        });
    }

    private void setUpSpinner() {
        ArrayAdapter<String> relationshipAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(relationship_array));
        relationshipAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        binding.relationshipTypeSpinner.setAdapter(relationshipAdapter);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(country_code_array));
        countryAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        binding.kinCountryCode.setAdapter(countryAdapter);
    }

    private int getRelationshipType(String relationship) {
        int type = 0;
        switch (relationship){
            case "Father": type=1; break;
            case "Mother": type = 2; break;
            case "Son": type = 3; break;
            case "Daughter": type = 4; break;
            case "Sister": type = 5; break;
            case "Guardian": type = 6; break;
            case "Foster/Step Parent": type = 7; break;
            case "Husband": type = 8; break;
            case "Brother": type = 9; break;
            case "Wife": type = 10; break;
            case "Grandparent": type =11; break;
            case "Other": type = 12; break;
        }
        return type;
    }
}
