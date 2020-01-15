package com.cti.lifego.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.databinding.FragmentPersonalDetailsBinding;
import com.cti.lifego.utils.StringUtils;
import com.cti.lifego.viewmodels.RegistrationViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import static com.cti.lifego.R.array.country_code_array;
import static com.cti.lifego.viewmodels.RegistrationViewModel.RegistrationState.COLLECT_KIN_DETAILS;

public class PersonalDetailsFragment extends BaseFragment{

    private RegistrationViewModel registrationViewModel;
    private Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    private FragmentPersonalDetailsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal_details, container, false);
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

        binding.selectDate.setOnClickListener((View v) -> {
            openDateDialog();
        });

        binding.nextButton.setOnClickListener(v -> {
            // When the next button is clicked, collect the current values from the two edit texts
            // and pass to the ViewModel to store.
            int genderInt = binding.userGender.getCheckedRadioButtonId();
            int optInt = binding.userOptIn.getCheckedRadioButtonId();
            RadioButton genderButton = (RadioButton) view.findViewById(genderInt);
            RadioButton optButton = (RadioButton) view.findViewById(optInt);
            String phone = StringUtils.concatStrings(binding.userCountryCode.getSelectedItem().toString(), binding.userPhoneNumber.getText().toString());
            if(genderButton==null){
                Toast.makeText(getContext(), "Select your gender", Toast.LENGTH_SHORT).show();
            }
            else if(optButton==null){
                Toast.makeText(getContext(), "Select your insurance option", Toast.LENGTH_SHORT).show();
            }
            else{
                registrationViewModel.collectPersonalData(phone, genderButton.getText().toString(), optButton.getText().toString());
            }

            // RegistrationViewModel updates the registrationState to
            // COLLECT_USER_PASSWORD when ready to move to the choose username and
            // password screen.
            registrationViewModel.getRegistrationState().observe(getViewLifecycleOwner(), state -> {
                if (state == COLLECT_KIN_DETAILS) {
                    if (navController.getCurrentDestination().getId() == R.id.personalDetailsFragment){
                        navController.navigate(R.id.personalDetailsFragment_to_kinDetailsFragment);
                    }
                }
            });

            // If the user presses back, cancel the user registration and pop back
            // to the login fragment. Since this ViewModel is shared at the activity
            // scope, its state must be reset so that it is in the initial state if
            // the user comes back to register later.
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            registrationViewModel.userCancelledRegistration();
                            navController.popBackStack(R.id.loginFragment, false);
                        }
                    });
        });
    }

    private void openDateDialog() {
        DatePickerDialog datePickerDialog =  new DatePickerDialog(Objects.requireNonNull(getActivity()),
                        dateListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateListener = (view, year, monthOfYear, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd/MM/YYYY";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        binding.userDob.setText(sdf.format(calendar.getTime()));
    };

    private void setUpSpinner() {
        ArrayAdapter<String> codeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(country_code_array));
        codeAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        binding.userCountryCode.setAdapter(codeAdapter);
    }

}
