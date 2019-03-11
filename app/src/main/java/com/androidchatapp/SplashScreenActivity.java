package com.androidchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread timer = new Thread() {
            @Override
            public void run() {

                super.run();
                try {
                    Thread.sleep(3000);

                } catch (Exception e) {
                    Log.e("Error message", e.getMessage());
                } finally {
                    Intent intent = new Intent(SplashScreenActivity.this,Login.class);
                    startActivity(intent);
                    finish();

                }
            }
        };
        timer.start();
    }
}
