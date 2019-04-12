package com.rootsoftit.pensiontracker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.data.Api;
import com.rootsoftit.pensiontracker.data.PensionClient;
import com.rootsoftit.pensiontracker.data.ResponsePacket;
import com.rootsoftit.pensiontracker.data.ServerResponse;
import com.rootsoftit.pensiontracker.data.User;

public class SignupFragment extends Fragment {

    private ConstraintLayout container;
    private TextView tvSignInTitle;
    private ProgressBar loading;
    private CardView cvInput;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private MaterialButton btnSignUp;
    private MaterialButton btnSignIn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        container = (ConstraintLayout) getView();
        tvSignInTitle = (TextView) container.findViewById(R.id.tvSignInTitle);
        loading = (ProgressBar) container.findViewById(R.id.loading);
        cvInput = (CardView) container.findViewById(R.id.cvInput);
        etUsername = (EditText) container.findViewById(R.id.etUsername);
        etEmail = (EditText) container.findViewById(R.id.etEmail);
        etPassword = (EditText) container.findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) container.findViewById(R.id.etConfirmPassword);
        btnSignUp = (MaterialButton) container.findViewById(R.id.btnSignUp);
        btnSignIn = (MaterialButton) container.findViewById(R.id.btnSignIn);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUp(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etConfirmPassword.getText().toString());

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                redirectToLogin();
            }
        });


    }

    private void redirectToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void signUp(String name, String email, String pass, String confirmPassword) {
        if (name.isEmpty()) {
            etUsername.setError("Please provide your name");
            return;
        }
        if (pass.isEmpty()) {
            etPassword.setError("Please provide a valid password");
            return;
        }
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Please provide a valid password");
            return;
        }
        if (email.isEmpty() || !email.contains("@")) {
            etEmail.setError("Please provide a valid Email address");
            return;
        }
        if (!pass.equals(confirmPassword)) {
            etPassword.setError("Passwords do not match");
            etConfirmPassword.setError("Passwords do not match");
            return;
        }
        loading.setVisibility(View.VISIBLE);
        btnSignUp.setEnabled(false);

        PensionClient client = Api.getInstance().getPensionClient();

        final User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(pass);
        user.setConfirmPassword(pass);

        client.register(user).enqueue(new ServerResponse<ResponsePacket<String>>(getContext()) {
            @Override
            public void OnComplete(ResponsePacket<String> response) {
                btnSignUp.setEnabled(true);
                loading.setVisibility(View.GONE);

                Toast.makeText(getContext(), response.getContent(), Toast.LENGTH_LONG).show();

                if (response.getContent().equals("Successfully registered")) {
                    redirectToLogin();
                }

            }

            @Override
            public void OnError(Error error) {

            }
        });
    }
}
