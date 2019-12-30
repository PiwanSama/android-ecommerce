/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cti.lifego.api.NetworkService;
import com.cti.lifego.api.RetrofitInstance;
import com.cti.lifego.models.LoginUser;
import com.cti.lifego.models.User;
import com.cti.lifego.repositories.StoreRepository;
import com.cti.lifego.repositories.UserRepository;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private Boolean userExists = false;

    public void createUser(User user) {
        userRepository.createUser(user);
    }

    public void loginUser(LoginUser user){
        userRepository.loginUser(user);
    }

    public Boolean getUserExists() {
        return userExists;
    }

    public void setUserExists(Boolean userExists) {
        this.userExists = userExists;
    }

}
