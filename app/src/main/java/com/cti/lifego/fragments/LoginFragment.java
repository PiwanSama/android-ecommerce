package com.cti.lifego.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.cti.lifego.R;
import com.cti.lifego.databinding.LoginFragmentBinding;
import com.cti.lifego.models.LoginUser;
import com.cti.lifego.viewmodels.LoginUserViewModel;
import com.google.gson.Gson;

import static com.cti.lifego.R.array.country_code_array;

public class LoginFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private LoginUserViewModel viewModel;
    private LoginFragmentBinding binding;
    private Boolean validated;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LoginUserViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        binding.setLifecycleOwner(this);
        binding.setLoginUser(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.home_fragment);
            }
        });

        binding.signInButton.setOnClickListener(v -> {
            String email = binding.userEmail.getText().toString();
            String password = binding.userPassword.getText().toString();
            if (email.isEmpty()){
                toast("Enter your email");
                validated = false;
            }
            else if (password.isEmpty()){
                toast("Enter your password");
                validated = false;
            }
            else {
                validated = true;
            }
            if (validated){
                LoginUser user = new LoginUser(email,password);
                Gson gson = new Gson();
                Log.i("Reg", gson.toJson(user));
                // viewModel.loginUser(user);
            }
        });
    }

    private void toast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
