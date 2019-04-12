package com.rootsoftit.pensiontracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rootsoftit.pensiontracker.data.Session;
import com.rootsoftit.pensiontracker.data.User;
import com.rootsoftit.pensiontracker.home.MainActivity;
import com.rootsoftit.pensiontracker.ui.login.LoginActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = Session.getUser();
                if (user != null) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 250); //set minimum of 150 ms , otherwise there will be jerking

        //printKeyHash();
    }
}