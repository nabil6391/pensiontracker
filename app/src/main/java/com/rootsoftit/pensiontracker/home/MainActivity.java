package com.rootsoftit.pensiontracker.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rootsoftit.pensiontracker.R;
import com.rootsoftit.pensiontracker.crud.PensionListFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new ProfileFragment()).commit();
                    return true;
                case R.id.navigation_reports:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new HomeFragment()).commit();
                    return true;
                case R.id.navigation_pensions:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new PensionListFragment()).commit();
                    return true;
                case R.id.navigation_contact:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new ContactFragment()).commit();
                    return true;
                case R.id.navigation_me:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new EditMyDetailsContactFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navView.setSelectedItemId(R.id.navigation_home);
    }

}
