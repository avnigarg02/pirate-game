package com.aclhacks.pirategame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class ShipwreckedPage extends AppCompatActivity
{

    private ImageButton settings;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fail);

        settings = findViewById(R.id.settings);
        home = findViewById(R.id.home);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShipwreckedPage.this, SettingsPage.class);
                startActivity(intent);
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShipwreckedPage.this, ChoicePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
