package com.rootsoftit.pensiontracker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.data.Api;
import com.rootsoftit.pensiontracker.data.PensionClient;
import com.rootsoftit.pensiontracker.data.ServerResponse;
import com.rootsoftit.pensiontracker.data.Session;
import com.rootsoftit.pensiontracker.data.User;
import com.rootsoftit.pensiontracker.data.UserResponse;
import com.rootsoftit.pensiontracker.home.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {
    View view;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    ProgressBar loadingProgressBar;
    private TextView btnForgotPassword;
    private TextView btnSignUp;
    private ActionListener actionListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        etUsername = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnLogin = view.findViewById(R.id.btnSignIn);

        btnForgotPassword = (TextView) view.findViewById(R.id.btnForgotPassword);
        btnSignUp = (TextView) view.findViewById(R.id.btnSignUp);


        btnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                etUsername.setText("nabil6391@gmail.com");
                etPassword.setText("63916391");
                return false;
            }
        });

        loadingProgressBar = view.findViewById(R.id.loading);


        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onForgotPassword();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onSignUp();
            }
        });

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login(etUsername.getText().toString(), etPassword.getText().toString());
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }


    private void login(String email, String pass) {
        if (email.isEmpty() || !email.contains("@")) {
            Toast.makeText(getContext(), "Please provide a valid Email address", Toast.LENGTH_SHORT).show();
            etUsername.setError("Please provide a valid Email address");
            etUsername.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            etPassword.setError("Please provide a valid password");
            return;
        }
        loadingProgressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        final PensionClient client = Api.getInstance().getPensionClient();

        final User user = new User();

        user.setEmail(email);
        user.setPassword(pass);

        client.login(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                btnLogin.setEnabled(true);
                loadingProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    client.getUserDetails().enqueue(new ServerResponse<UserResponse>(getContext()) {
                        @Override
                        public void OnComplete(UserResponse response) {


                            Session.setUser(response.getUser());

                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }

                        @Override
                        public void OnError(Error error) {

                        }
                    });
                    String token = response.body().getAccessToken();
                    Session.setToken(token);
                } else {
                    Toast.makeText(getContext(), "Email or password does not match", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    public void setListener(ActionListener actionListener) {

        this.actionListener = actionListener;
    }

    public interface ActionListener {

        void onSignUp();

        void onForgotPassword();


    }

}
