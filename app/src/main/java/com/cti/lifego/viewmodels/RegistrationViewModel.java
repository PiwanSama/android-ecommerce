/*
 * Copyright (c) 2020. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.text.TextUtils;
import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.User;
import com.cti.lifego.repositories.UserRepository;

import java.util.Objects;

public class RegistrationViewModel extends ViewModel {

    public enum RegistrationState{
        COLLECT_PERSONAL_DETAILS,
        COLLECT_KIN_DETAILS,
        REGISTRATION_COMPLETE
    }

    public enum PersonalFormErrors {
        MISSING_GIVEN_NAME,
        MISSING_SURNAME,
        MISSING_EMAIL,
        MISSING_PASSWORD,
        MISSING_DOB,
        MISSING_PHONE,
        MISSING_GENDER,
        MISSING_OPT_IN
    }

    public enum KinFormErrors {
        MISSING_NAME,
        MISSING_PHONE_NUMBER,
    }

    private UserRepository userRepository = UserRepository.getInstance();

    public MutableLiveData<String> given_name = new MutableLiveData<>();
    public MutableLiveData<String> surname = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> dob = new MutableLiveData<>();
    public MutableLiveData<String> phone_string = new MutableLiveData<>();
    private MutableLiveData<String> phone_contact = new MutableLiveData<>();
    private MutableLiveData<String> gender = new MutableLiveData<>();
    private MutableLiveData<String> opt = new MutableLiveData<>();
    public ObservableArrayList<PersonalFormErrors> personalFormErrors = new ObservableArrayList<>();

    public MutableLiveData<String> kin_name = new MutableLiveData<>();
    public MutableLiveData<String> kin_phone_string = new MutableLiveData<>();
    private MutableLiveData<String> kin_phone_contact = new MutableLiveData<>();
    private MutableLiveData<Integer> kin_relationship = new MutableLiveData<>();
    public ObservableArrayList<KinFormErrors> kinFormErrors = new ObservableArrayList<>();

    private MutableLiveData<RegistrationState> registrationState = new MutableLiveData<>(RegistrationState.COLLECT_PERSONAL_DETAILS);

    public MutableLiveData<RegistrationState> getRegistrationState(){
        return registrationState;
    }


    public void collectPersonalData(String mPhone, String mGender, String mOpt) {
        // validate data
        if(isPersonalFormValid()) {
            //store data
            phone_contact.setValue(mPhone);
            gender.setValue(mGender);
            opt.setValue(mOpt);
            // Change State to collecting username and password
            registrationState.setValue(RegistrationState.COLLECT_KIN_DETAILS);
        }

    }

    public void collectKinData(String phone, int relationshipType) {
        // ... validate and store data
        // ... create account
        // ... authenticate
        if (isKinFormValid()){
            kin_phone_contact.setValue(phone);
            kin_relationship.setValue(relationshipType);
            User newUser = new User(given_name.getValue(), surname.getValue(), email.getValue(), phone, dob.getValue(), gender.getValue(), opt.getValue(), password.getValue(), kin_name.getValue(), kin_phone_contact.getValue(), relationshipType);

           // userRepository.createUser(newUser)
            // Change State to collecting username and password if successful callback
          //  registrationState.setValue( RegistrationState.REGISTRATION_COMPLETE);
        }
    }

    private boolean isPersonalFormValid(){
        personalFormErrors.clear();
        if (TextUtils.isEmpty(given_name.getValue())){
            personalFormErrors.add(PersonalFormErrors.MISSING_GIVEN_NAME);
        }
        else if (TextUtils.isEmpty(surname.getValue())){
            personalFormErrors.add(PersonalFormErrors.MISSING_SURNAME);
        }
        else if (!isValidEmail(email.getValue()) || (TextUtils.isEmpty(email.getValue()))){
            personalFormErrors.add(PersonalFormErrors.MISSING_EMAIL);
        }
        else if (TextUtils.isEmpty(password.getValue()) || !isValidPassword(Objects.requireNonNull(password.getValue()))){
            personalFormErrors.add(PersonalFormErrors.MISSING_PASSWORD);
        }
        else if (TextUtils.isEmpty(dob.getValue())){
            personalFormErrors.add(PersonalFormErrors.MISSING_DOB);
        }
        else if (TextUtils.isEmpty(phone_string.getValue())){
            personalFormErrors.add(PersonalFormErrors.MISSING_PHONE);
        }
        return personalFormErrors.isEmpty();
    }

    private boolean isKinFormValid(){
        kinFormErrors.clear();
        if (TextUtils.isEmpty(kin_name.getValue())){
            kinFormErrors.add(KinFormErrors.MISSING_NAME);
        }
        else if (TextUtils.isEmpty(kin_phone_string.getValue())){
            kinFormErrors.add(KinFormErrors.MISSING_PHONE_NUMBER);
        }
        return kinFormErrors.isEmpty();
    }

    public boolean userCancelledRegistration() {
        // Clear existing registration data
        registrationState.setValue(RegistrationState.COLLECT_PERSONAL_DETAILS);
        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length()>8;
    }
}
