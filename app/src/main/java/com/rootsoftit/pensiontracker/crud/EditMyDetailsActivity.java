package com.rootsoftit.pensiontracker.crud;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.data.Api;
import com.rootsoftit.pensiontracker.data.PensionClient;
import com.rootsoftit.pensiontracker.data.Session;
import com.rootsoftit.pensiontracker.data.User;

import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMyDetailsActivity extends AppCompatActivity {

    public static String MODE = "mode";

    MaterialButton btnSave;
    private ImageView btnBack;
    private TextView tvTitle;
    private TextView tvUsername;
    private EditText etUsername;
    private TextView tvPassword;
    private EditText etPassword;
    private TextView tvEmail;
    private EditText etEmail;

    private TextView tvConfirmPassword;
    private EditText etConfirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mydetails);

        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvUsername = findViewById(R.id.tvUsername);
        etUsername = findViewById(R.id.etUsername);

        tvEmail = findViewById(R.id.tvEmail);
        etEmail = findViewById(R.id.etEmail);
        tvPassword = findViewById(R.id.tvPassword);
        etPassword = findViewById(R.id.etPassword);
        tvConfirmPassword = findViewById(R.id.tvConfirmPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnSave = findViewById(R.id.btnSave);
        User user = Session.getUser();

        etUsername.setText(user.getName());
        etEmail.setText(user.getEmail());

        etPassword.setText(user.getPassword());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(etUsername.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString(), etConfirmPassword.getText().toString());
            }
        });

    }

    private void signUp(String name, String email, String pass, String confirmPassword) {
        if (name.isEmpty()) {
            etUsername.setError("Please provide your name");
            return;
        }
        if (pass.isEmpty() || pass.length() < 8) {
            etPassword.setError("Please provide a valid password with minimum 8 Characters");
            return;
        }
        if (confirmPassword.isEmpty() || confirmPassword.length() < 8) {
            etConfirmPassword.setError("Please provide a valid password with minimum 8 Characters");
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
//        loading.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);

        PensionClient client = Api.getInstance().getPensionClient();

        final User user = new User();

        user.setEmail(email);
        user.setPassword(pass);
        FormBody build = new FormBody.Builder()
                .add("email", etEmail.getText().toString())
                .add("username", etUsername.getText().toString())
                .add("password", etPassword.getText().toString())
                .add("repassword", etConfirmPassword.getText().toString())
                .build();


        client.editMyDetails(build).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                btnSave.setEnabled(true);
//                loading.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Succesfully Registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
