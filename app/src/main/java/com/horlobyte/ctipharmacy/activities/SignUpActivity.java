package com.horlobyte.ctipharmacy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResponse;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.horlobyte.ctipharmacy.R;

import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    CredentialsClient credentialsClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Intent openApp = new Intent(SignUpActivity.this, SearchActivity.class);
                startActivity(openApp);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Credential credential = new Credential.Builder(user.getEmail())
                        .setAccountType(IdentityProviders.GOOGLE)
                        .setName(user.getDisplayName())
                        .build();
                credentialsClient.save(credential).addOnCompleteListener(
                        new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()) {
                                    Log.d("APP", "SAVE: OK");
                                }
                            }
                        });
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(this,"Sign In Cancelled", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this,"Sign In Failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show();
                Log.e("APP", "Sign-in error: ", response.getError());
            }
        }
    }

    private void onCredentialRetrieved(Credential credential) {
        String accountType = credential.getAccountType();
        if (accountType == null) {
            // Sign the user in with information from the Credential.
            //signInWithPassword(credential.getId(), credential.getPassword());
        } else if (accountType.equals(IdentityProviders.GOOGLE)) {
            // The user has previously signed in with Google Sign-In. Silently
            // sign in the user with the same ID.
            // See https://developers.google.com/identity/sign-in/android/
            GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();

            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);
            Task<GoogleSignInAccount> task = signInClient.silentSignIn();
            // ...
        }
    }
}
