package com.rootsoftit.pensiontracker.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.data.Api;
import com.rootsoftit.pensiontracker.data.PensionClient;
import com.rootsoftit.pensiontracker.data.ResponsePacket;
import com.rootsoftit.pensiontracker.data.ServerResponse;
import com.rootsoftit.pensiontracker.utils.ProgressDialogUtility;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ContactFragment extends Fragment {

    private TextView tvEmail;
    private EditText etEmail;
    private TextView tvDescription;
    private EditText etDescription;

    private MaterialButton btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_contactus, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        tvEmail = view.findViewById(R.id.tvEmail);
        etEmail = view.findViewById(R.id.etEmail);
        tvDescription = view.findViewById(R.id.tvDescription);
        etDescription = view.findViewById(R.id.etPassword);


        btnSubmit = view.findViewById(R.id.btnSubmit);




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String pass = etDescription.getText().toString();

                if (email.isEmpty() || !email.contains("@")) {
                    Toast.makeText(getContext(), "Please provide a valid Email address", Toast.LENGTH_SHORT).show();
                    etEmail.setError("Please provide a valid Email address");
                    etEmail.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {
                    etDescription.setError("Please enter some description");
                    return;
                }

                PensionClient client = Api.getInstance().getPensionClient();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", email);
                    jsonObject.put("description", pass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                // put your json here
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());

                ProgressDialogUtility.show(getContext());

                client.contact(body).enqueue(new ServerResponse<ResponsePacket<String>>(getContext()) {
                    @Override
                    public void OnComplete(ResponsePacket<String> response) {
//                        ProgressDialogUtility.setMessage(response.getContent());
                        ProgressDialogUtility.dismiss();
                        Toast.makeText(getContext(), response.getContent(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnError(Error error) {
                        ProgressDialogUtility.dismiss();
                    }
                });
            }
        });


    }
}
