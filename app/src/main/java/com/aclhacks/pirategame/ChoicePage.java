package com.aclhacks.pirategame;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class ChoicePage extends AppCompatActivity {

    private Button optionA;
    private Button optionB;
    private Button optionC;
    private ImageButton settings;
    private Button startTimer;
    private EditText inputHr;
    private EditText inputMin;
    private TextView coinsText;
    private int coins = 0;
    private int time = 0;
    private float scaler = 1f;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice);

        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        settings = findViewById(R.id.settings);
        startTimer = findViewById(R.id.startTimer);
        inputHr = findViewById(R.id.inputHr);
        inputMin = findViewById(R.id.inputMin);
        coinsText = findViewById(R.id.coinsText);

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               scaler = 1f;
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaler = 1.5f;
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaler = 2f;
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set content view to settings page
                Intent intent = new Intent(ChoicePage.this, SettingsPage.class);
                startActivity(intent);
                finish();
            }
        });

        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set content view to map
                Intent intent = new Intent(ChoicePage.this, PathPage.class);
                intent.putExtra("coins", coins);
                intent.putExtra("time", time);
                startActivity(intent);
                finish();
            }
        });

        inputHr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                time = (int) (Float.parseFloat(inputHr.getText().toString()) * 60 + Float.parseFloat(inputMin.getText().toString()));
                coins = (int) (time * scaler);
                coinsText.setText(String.format("%d coins", coins));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        inputMin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                time = (int) (Float.parseFloat(inputHr.getText().toString()) * 60 + Float.parseFloat(inputMin.getText().toString()));
                coins = (int) (time * scaler);
                coinsText.setText(String.format("%d coins", coins));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

}
