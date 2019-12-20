/*
 * Copyright (c) 2019. Written by Samalie Piwan
 */

package com.cti.lifego.viewmodels;

import androidx.lifecycle.ViewModel;

import com.cti.lifego.models.User;
import com.cti.lifego.repositories.StoreRepository;
import com.cti.lifego.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository = UserRepository.getInstance();
    private Boolean userExists = false;
    private User user;

    public void setRegistrationUser(User user) {
        this.user = user;
    }

    public void setLoginUser(User user) {
        this.user = user;
    }

    public Boolean getUserExists() {
        return userExists;
    }

    public void setUserExists(Boolean userExists) {
        this.userExists = userExists;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
