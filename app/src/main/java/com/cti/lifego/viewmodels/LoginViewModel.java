/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.text.TextUtils;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.repositories.UserRepository;

public class LoginViewModel extends ViewModel {

    public final MutableLiveData<AuthenticationState> authenticationState =
            new MutableLiveData<>();

    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public ObservableArrayList<LoginFormErrors> loginFormErrors = new ObservableArrayList<>();

    public enum AuthenticationState {
        UNAUTHENTICATED,        // Initial state, the user needs to authenticate
        AUTHENTICATED,          // The user has authenticated successfully
        INVALID_AUTHENTICATION  // Authentication failed
    }

    public enum LoginFormErrors {
        MISSING_EMAIL,
        MISSING_PASSWORD
    }

    public void loginUser(String email, String password) {
        if (isFormValid()) {
            UserRepository userRepository = UserRepository.getInstance();
            // userRepository.loginUser(user);
            // if the user is authorized
            authenticationState.setValue(AuthenticationState.AUTHENTICATED);
        }
        else{
            authenticationState.setValue(AuthenticationState.INVALID_AUTHENTICATION);
        }
    }

    public void refuseAuthentication() {
        authenticationState.setValue(AuthenticationState.UNAUTHENTICATED);
    }

    private boolean isFormValid(){
        loginFormErrors.clear();
        if(TextUtils.isEmpty(email.getValue())){
            loginFormErrors.add(LoginFormErrors.MISSING_EMAIL);
        }
        else if (TextUtils.isEmpty(password.getValue())){
            loginFormErrors.add(LoginFormErrors.MISSING_PASSWORD);
        }
        return loginFormErrors.isEmpty();
    }
}



