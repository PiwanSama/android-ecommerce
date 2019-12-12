package com.cti.lifego.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cti.lifego.R;
import com.cti.lifego.databinding.RegistrationFragmentBinding;
import com.cti.lifego.models.User;
import com.cti.lifego.utils.Utils;
import com.cti.lifego.viewmodels.UserRegistrationViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.cti.lifego.R.array.country_code_array;

public class RegistrationFragment extends Fragment{

    private Spinner user_phone_spinner, kin_phone_spinner;
    private UserRegistrationViewModel viewModel;
    private Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    private RegistrationFragmentBinding binding;
    private RadioButton genderRadioButton;
    private RadioButton optRadioButton;
    private Boolean validated;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.inflate(inflater, R.layout.registration_fragment, container, false);
        View v = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(UserRegistrationViewModel.class);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_phone_spinner = binding.userCountryCode;
        kin_phone_spinner = binding.kinCountryCode;
        RadioGroup genderGroup = binding.userGender;
        RadioGroup optGroup = binding.userOptIn;

        setUpPhoneSpinner();

        User user = new User();
        Utils utils = new Utils();

        binding.userDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validated = true;
                int genderId = genderGroup.getCheckedRadioButtonId();
                int optInId = optGroup.getCheckedRadioButtonId();
                genderRadioButton = view.findViewById(genderId);
                optRadioButton = view.findViewById(optInId);

                String user_phone_text = binding.userPhoneNumber.getText().toString();
                String kin_phone_text = binding.kinPhoneNumber.getText().toString();
                String email = binding.userEmail.getText().toString();
                String given_name = binding.userGivenName.getText().toString();
                String surname = binding.userSurname.getText().toString();
                String password = binding.userPassword.getText().toString();
                String dob = binding.userDob.getText().toString();
                String kin_name = binding.nextOfKinName.getText().toString();
                String r_type = binding.relationshipType.getText().toString();

                if (given_name.isEmpty()){
                    toast("Enter your given name");
                    validated = false;
                }
                else if (surname.isEmpty()){
                    toast("Enter your surname");
                    validated = false;
                }
                else if (!isValidEmail(email)){
                    toast("Enter your email");
                    validated = false;
                }
                else if (password.isEmpty() || password.length()<8){
                    toast("Enter a valid password");
                    validated = false;
                }
                else if (dob.isEmpty()){
                    toast("Select a date of birth");
                    validated = false;
                }
                else if (user_phone_text.isEmpty() || user_phone_text.length()<9){
                    toast("Enter a valid phone number");
                    validated = false;
                }
                else if (genderRadioButton==null){
                    toast("Select your gender");
                    validated = false;
                }
                else if (optRadioButton==null){
                    toast("Select your opt-in option");
                    validated = false;
                }
                else if (kin_name.isEmpty()){
                    toast("Enter your next of kin name");
                    validated = false;
                }
                else if (kin_phone_text.isEmpty() || kin_phone_text.length()<9){
                    toast("Enter a valid kin phone number");
                    validated = false;
                }
                else if (r_type.isEmpty()){
                    toast("Enter your next of kin relationship");
                    validated = false;
                }
                else{
                    validated = true;
                }

                if (validated){
                    user.givenName = given_name;
                    user.surname = surname;
                    user.email = email;
                    user.password = password;
                    user.dob = dob;
                    user.next_of_kin_name = kin_name;
                    user.phone = utils.getPhoneNumber(user_phone_spinner.getSelectedItem().toString(), user_phone_text);
                    user.kin_phone_number = utils.getPhoneNumber(kin_phone_spinner.getSelectedItem().toString(), kin_phone_text);
                    user.gender = genderRadioButton.getText().toString();
                    user.opt_in = genderRadioButton.getText().toString();
                    user.relationship_type = r_type;
                    viewModel.createUser(user);
                }
            }
        });
    }

    private void toast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String myFormat = "dd/MM/YYYY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        binding.userDob.setText(sdf.format(calendar.getTime()));
    };

    private void setUpPhoneSpinner() {
        ArrayAdapter<String> codeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(country_code_array));
        codeAdapter.setDropDownViewResource(R.layout.custom_spinner_item);
        user_phone_spinner.setAdapter(codeAdapter);
        kin_phone_spinner.setAdapter(codeAdapter);
    }
}
