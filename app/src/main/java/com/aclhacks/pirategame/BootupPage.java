package com.aclhacks.pirategame;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BootupPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bootup);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    Intent intent = new Intent(BootupPage.this, LoginPage.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
