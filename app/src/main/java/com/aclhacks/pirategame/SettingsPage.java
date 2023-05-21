package com.aclhacks.pirategame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsPage extends AppCompatActivity {

    private Button changePassword;
    private Button changeUsername;
    private Button changeEmail;
    private Button statistics;
    private Button friends;
    private Button back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingspage);

        back = findViewById(R.id.back);
        changePassword = findViewById(R.id.changePassword);
        changeUsername = findViewById(R.id.changeUsername);
        changeEmail = findViewById(R.id.changeEmail);
        statistics = findViewById(R.id.statistics);
        friends = findViewById(R.id.friends);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsPage.this, ChoicePage.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
