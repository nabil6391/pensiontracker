package com.rootsoftit.pensiontracker.ui.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rootsoftit.pensiontracker.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        SignInFragment signInFragment = new SignInFragment();

        signInFragment.setListener(new SignInFragment.ActionListener() {
            @Override
            public void onSignUp() {
                getSupportFragmentManager().beginTransaction().add(R.id.content, new SignupFragment()).commit();
            }

            @Override
            public void onForgotPassword() {
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new ForgotPasswordFragment()).commit();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.content, signInFragment).commit();


    }


}
