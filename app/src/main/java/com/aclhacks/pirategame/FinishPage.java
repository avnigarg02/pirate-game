package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.database.sqlite.SQLiteDatabase;
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

        int coins = getIntent().getIntExtra("coins", 0);
        coinsText.setText(String.format("+%d coins", coins));

        long userId = getIntent().getLongExtra("userId", 0);
        UserDataDbHelper mDbHelper = new UserDataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserInfo.UserEntry.COINS, coins);
        db.update(UserInfo.UserEntry.TABLE_NAME, values, "_id = " + userId, null);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishPage.this, ChoicePage.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });

    }

}
