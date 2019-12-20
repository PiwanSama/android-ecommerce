/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.LoginUser;
import com.cti.lifego.repositories.UserRepository;

public class LoginUserViewModel extends ViewModel {

    private UserRepository userRepository = UserRepository.getInstance();
    public MutableLiveData<String> email = new MutableLiveData<>();;
    public MutableLiveData<String> password = new MutableLiveData<>();

    public void loginUser(LoginUser user){
        userRepository.loginUser(user);
    }
}
