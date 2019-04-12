package com.rootsoftit.pensiontracker.crud;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.data.Api;
import com.rootsoftit.pensiontracker.data.Pension;
import com.rootsoftit.pensiontracker.data.PensionClient;
import com.rootsoftit.pensiontracker.data.ServerResponse;

public class PensionDetailsActivity extends AppCompatActivity {
    public static String PENSION = "pension";

    public boolean isEdit = false;
    Pension pension;
    private TextView tvPolicyName;
    private EditText etPolicyName;
    private TextView tvCompany;
    private EditText etCompany;
    private TextView tvPolicyNumber;
    private EditText etPolicyNumber;
    private TextView tvPolicyStarted;
    private EditText etPolicyStarted;
    private TextView tvAmount;
    private EditText etAmount;
    private TextView tvCompanyofEmployment;
    private EditText etCompanyofEmployment;
    private TextView tvOtherDetails;
    private EditText etOtherDetails;
    private ImageView btnBack;
    private TextView tvTitle;

    private static void isTextNotEmpty(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("This field is required");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pension_details);


        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvPolicyNumber = findViewById(R.id.tvPolicyNumber);
        etPolicyNumber = findViewById(R.id.etPolicyNumber);
        tvCompany = findViewById(R.id.tvCompany);
        etCompany = findViewById(R.id.etCompany);
        tvPolicyName = findViewById(R.id.tvPolicyName);
        etPolicyName = findViewById(R.id.etPolicyName);
        tvPolicyStarted = findViewById(R.id.tvPolicyStarted);
        etPolicyStarted = findViewById(R.id.etPolicyStarted);
        tvAmount = findViewById(R.id.tvAmount);
        etAmount = findViewById(R.id.etAmount);
        tvCompanyofEmployment = findViewById(R.id.tvCompanyofEmployment);
        etCompanyofEmployment = findViewById(R.id.etCompanyofEmployment);
        tvOtherDetails = findViewById(R.id.tvOtherDetails);
        etOtherDetails = findViewById(R.id.etOtherDetails);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pension = getIntent().getParcelableExtra(PENSION);

        etPolicyName.setText(pension.getPolicyName());
        etPolicyNumber.setText(pension.getPolicyNumber());
        etPolicyStarted.setText(pension.getPolicyStart());

        etCompany.setText(pension.getCompanyName());
        etCompanyofEmployment.setText(pension.getCompanyOfEmployment());
        etAmount.setText(pension.getAmount());

        etPolicyName.setEnabled(false);
        etPolicyNumber.setEnabled(false);
        etPolicyStarted.setEnabled(false);

        etCompany.setEnabled(false);
        etCompanyofEmployment.setEnabled(false);
        etAmount.setEnabled(false);
    }

    private void onAdd() {
        isTextNotEmpty(etPolicyName);
        isTextNotEmpty(etPolicyNumber);
        isTextNotEmpty(etPolicyStarted);
        isTextNotEmpty(etCompany);
        isTextNotEmpty(etCompanyofEmployment);
        isTextNotEmpty(etAmount);

        PensionClient pensionClient = Api.getInstance().getPensionClient();

        Pension pension = new Pension();

        pension.setPolicyName(etPolicyName.getText().toString());
        pension.setCompanyName(etCompany.getText().toString());
        pension.setPolicyNumber(etPolicyNumber.getText().toString());
        pension.setPolicyStart(etPolicyStarted.getText().toString());
        pension.setCompanyOfEmployment(etCompanyofEmployment.getText().toString());
        pension.setAmount(etAmount.getText().toString());

        pensionClient.addPension(pension).enqueue(new ServerResponse<Pension>(getApplicationContext()) {
            @Override
            public void OnComplete(Pension response) {


            }

            @Override
            public void OnError(Error error) {

            }
        });

    }


}
