package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FinishPage extends AppCompatActivity {

    private Button home;
    private TextView coinsText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yay);

        home = findViewById(R.id.home);
        coinsText = findViewById(R.id.textView7);

        coinsText.setText(String.format("+%d coins",
                getIntent().getIntExtra("coins", 0)));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishPage.this, ChoicePage.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
