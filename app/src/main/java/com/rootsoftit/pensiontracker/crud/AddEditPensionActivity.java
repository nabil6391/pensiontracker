package com.rootsoftit.pensiontracker.crud;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.data.Api;
import com.rootsoftit.pensiontracker.data.Pension;
import com.rootsoftit.pensiontracker.data.PensionClient;
import com.rootsoftit.pensiontracker.data.ServerResponse;
import com.rootsoftit.pensiontracker.utils.DecimalDigitsInputFilter;
import com.rootsoftit.pensiontracker.utils.ProgressDialogUtility;

import java.util.Calendar;

import static com.rootsoftit.pensiontracker.crud.PensionDetailsActivity.PENSION;

public class AddEditPensionActivity extends AppCompatActivity {

    public static String MODE = "mode";
    public boolean isEdit = false;
 MaterialButton btnAddOrEdit;
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
    private Pension pension;

    private static boolean isTextNotEmpty(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("This field is required");
            return true;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pension);


        tvPolicyName = findViewById(R.id.tvPolicyName);
        etPolicyName = findViewById(R.id.etPolicyName);
        tvCompany = findViewById(R.id.tvCompany);
        etCompany = findViewById(R.id.etCompany);
        tvPolicyNumber = findViewById(R.id.tvPolicyNumber);
        etPolicyNumber = findViewById(R.id.etPolicyNumber);
        tvPolicyStarted = findViewById(R.id.tvPolicyStarted);
        etPolicyStarted = findViewById(R.id.etPolicyStarted);
        tvAmount = findViewById(R.id.tvAmount);
        etAmount = findViewById(R.id.etAmount);
        tvCompanyofEmployment = findViewById(R.id.tvCompanyofEmployment);
        etCompanyofEmployment = findViewById(R.id.etCompanyofEmployment);
        tvOtherDetails = findViewById(R.id.tvOtherDetails);
        etOtherDetails = findViewById(R.id.etOtherDetails);
        btnAddOrEdit = findViewById(R.id.btnSave);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog dialog = new DatePickerDialog(AddEditPensionActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                month += 1;
                String monthS = month <= 9 ? "0" + month : month + "";
                String dayS = day <= 9 ? "0" + day : day + "";

                String date = year + "-" + monthS + "-" + dayS;

                etPolicyStarted.setText(date);
            }
        }, year, month, day){

            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                switch (which) {
                    case BUTTON_POSITIVE:
                        super.onClick(dialog, which);
                        dismiss();
                        break;
                    case BUTTON_NEGATIVE:
                        cancel();
                        break;
                }
            }
        };


        etPolicyStarted.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (!dialog.isShowing()) {
                    dialog.show();
                }

                return true;
            }
        });

        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        isEdit = getIntent().getBooleanExtra(MODE, false);

        if (isEdit) {
            tvTitle.setText("Edit a Pension");
            btnAddOrEdit.setText("Save");
//            btnAddOrEdit.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_edit_black_24dp), null, null, null);
            btnAddOrEdit.setIconResource(R.drawable.ic_save_black_24dp);

            pension = getIntent().getParcelableExtra(PENSION);

            etPolicyName.setText(pension.getPolicyName());
            etPolicyNumber.setText(pension.getPolicyNumber());
            etPolicyStarted.setText(pension.getPolicyStart());

            etCompany.setText(pension.getCompanyName());
            etCompanyofEmployment.setText(pension.getCompanyOfEmployment());
            etAmount.setText(pension.getAmount());

        } else {
            tvTitle.setText("Add a Pension");
            btnAddOrEdit.setText("Add");
//            btnAddOrEdit.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.ic_add_black_24dp), null, null, null);
            btnAddOrEdit.setIconResource(R.drawable.ic_add_black_24dp);

            pension = new Pension();
        }
        etAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(100,2)});

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnAddOrEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInValid()) {
                    return;
                }


                pension.setPolicyName(etPolicyName.getText().toString());
                pension.setCompanyName(etCompany.getText().toString());
                pension.setPolicyNumber(etPolicyNumber.getText().toString());
                pension.setPolicyStart(etPolicyStarted.getText().toString());
                pension.setCompanyOfEmployment(etCompanyofEmployment.getText().toString());
                pension.setAmount(etAmount.getText().toString());

                if (isEdit) {


                    onEdit(pension);
                } else {
                    onAdd(pension);
                }

            }


        });

    }

    private void onEdit(Pension pension) {
        PensionClient pensionClient = Api.getInstance().getPensionClient();
        btnAddOrEdit.setEnabled(false);


        ProgressDialogUtility.show(AddEditPensionActivity.this);
        pensionClient.updatePension(pension.getId(), pension).enqueue(new ServerResponse<Pension>(getApplicationContext()) {
            @Override
            public void OnComplete(Pension response) {
                btnAddOrEdit.setEnabled(true);

                Toast.makeText(AddEditPensionActivity.this, "Success", Toast.LENGTH_SHORT).show();
                ProgressDialogUtility.dismiss();

                finish();
            }

            @Override
            public void OnError(Error error) {
                ProgressDialogUtility.dismiss();
            }
        });
    }

    private void onAdd(Pension pension) {
        PensionClient pensionClient = Api.getInstance().getPensionClient();
        btnAddOrEdit.setEnabled(false);


        ProgressDialogUtility.show(AddEditPensionActivity.this);

        pensionClient.addPension(pension).enqueue(new ServerResponse<Pension>(getApplicationContext()) {
            @Override
            public void OnComplete(Pension response) {
                btnAddOrEdit.setEnabled(true);
                ProgressDialogUtility.dismiss();

                Toast.makeText(AddEditPensionActivity.this, "Success", Toast.LENGTH_SHORT).show();

                finish();
            }

            @Override
            public void OnError(Error error) {
                ProgressDialogUtility.dismiss();
            }
        });

    }

    private boolean isInValid() {
        return isTextNotEmpty(etPolicyNumber) &&
                isTextNotEmpty(etPolicyStarted) &&
                isTextNotEmpty(etCompany) &&
                isTextNotEmpty(etCompanyofEmployment) &&
                isTextNotEmpty(etAmount);
    }


}
