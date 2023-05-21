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
    private float scalar = 1f;
    private int hours = 0;
    private int minutes = 0;

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
               scalar = 1f;
                coins = (int) (time * scalar);
                coinsText.setText(String.format("%d coins", coins));
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scalar = 1.5f;
                coins = (int) (time * scalar);
                coinsText.setText(String.format("%d coins", coins));
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scalar = 2f;
                coins = (int) (time * scalar);
                coinsText.setText(String.format("%d coins", coins));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set content view to settings page
                Intent intent = new Intent(ChoicePage.this, SettingsPage.class);
                intent.putExtra("userId", getIntent().getLongExtra("userId", 0));
                startActivity(intent);
            }
        });

        startTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set content view to map
                Intent intent = new Intent(ChoicePage.this, PathPage.class);
                intent.putExtra("coins", coins);
                intent.putExtra("time", time);
                intent.putExtra("start", System.currentTimeMillis());
                intent.putExtra("userId", getIntent().getLongExtra("userId", 0));
                startActivity(intent);
                finish();
            }
        });

        inputHr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!inputHr.getText().toString().equals("")) {
                    hours = Integer.parseInt(inputHr.getText().toString());
                } else {
                    hours = 0;
                }
                time = hours * 60 + minutes;
                coins = (int) (time * scalar);
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
                if (!inputMin.getText().toString().equals("")) {
                    minutes = Integer.parseInt(inputMin.getText().toString());
                } else {
                    minutes = 0;
                }
                time = hours * 60 + minutes;
                coins = (int) (time * scalar);
                coinsText.setText(String.format("%d coins", coins));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }

}
