package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.databinding.UserDetailsBinding;
import com.cti.lifego.viewmodels.LoginViewModel;

public class UserDetailsFragment extends Fragment {

    UserDetailsBinding binding;
    ProgressBar loadingUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavController navController = Navigation.findNavController(container);
        binding = DataBindingUtil.inflate(inflater, R.layout.user_details, container, false);
        loadingUser = binding.loadingUser;
        loadingUser.setVisibility(View.VISIBLE);
        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.authenticationState.observe(getViewLifecycleOwner(), authenticationState -> {
            switch (authenticationState){
                case AUTHENTICATED:
                    //continue with the process
                case UNAUTHENTICATED:
                    navController.navigate(R.id.loginFragment);
            }
        });
        return binding.getRoot();
    }
}
