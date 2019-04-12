package com.rootsoftit.pensiontracker.crud;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rootsoftit.pensiontracker.R;

import static com.rootsoftit.pensiontracker.crud.AddEditPensionActivity.MODE;

public class AllPensionActivity extends AppCompatActivity {

    public static final int INFO = 0;
    public static final int DELETE = 1;
    public static final int EDIT = 2;

    public int mode = INFO;
    private ImageView btnBack;
    private TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_delete_pension);

        PensionListFragment fragment = new PensionListFragment();

        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mode = getIntent().getIntExtra(MODE, INFO);

        switch (mode) {
            case INFO:
               tvTitle.setText("All Pensions");
                break;
            case EDIT:
                tvTitle.setText("Edit a Pension");
                break;
            case DELETE:
                tvTitle.setText("Delete a pension");
                break;
        }

        fragment.setMode(mode);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();


    }


}
