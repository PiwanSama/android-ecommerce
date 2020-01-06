package com.cti.lifego.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.databinding.LoginFragmentBinding;
import com.cti.lifego.viewmodels.LoginViewModel;

public class LoginFragment extends BaseFragment {
    private LoginFragmentBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);


        binding.skip.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.homeFragment));

        binding.signInButton.setOnClickListener(v -> {
            String email = binding.userEmail.getText().toString();
            String password = binding.userPassword.getText().toString();
            loginViewModel.loginUser(email, password);
        });

        binding.noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.user_registration_graph);
            }
        });
    }

}
