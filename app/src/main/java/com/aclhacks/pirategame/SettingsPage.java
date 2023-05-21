package com.aclhacks.pirategame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsPage extends AppCompatActivity {

    private Button statistics;
    private Button friends;
    private Button changePassword;
    private Button changeEmail;
    private Button logout;
    private Button back;
    private TextView coinsText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingspage);

        back = findViewById(R.id.back);
        changePassword = findViewById(R.id.changePassword);
        logout = findViewById(R.id.logout);
        changeEmail = findViewById(R.id.changeEmail);
        statistics = findViewById(R.id.statistics);
        friends = findViewById(R.id.friends);
        coinsText = findViewById(R.id.totalcoins);

        long userId = getIntent().getLongExtra("userId", 0);
        System.out.println(userId);
        UserDataDbHelper mDbHelper = new UserDataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //read coins from database at userId
        //set coinsText to coins
        UserDataDbHelper mDbHelper2 = new UserDataDbHelper(this);
        SQLiteDatabase db2 = mDbHelper2.getReadableDatabase();

        String[] projection2 = {
                UserInfo.UserEntry.FULL_NAME,
                UserInfo.UserEntry.EMAIL,
                UserInfo.UserEntry.ALLOWED_APPS,
                UserInfo.UserEntry.BLOCKED_APPS,
                UserInfo.UserEntry.USAGE_HISTORY,
                UserInfo.UserEntry.COINS
        };

        String selection2 = UserInfo.UserEntry._ID + " = ?";
        String[] selectionArgs2 = { String.valueOf(userId) };


        Cursor cursor2 = db2.query(
                UserInfo.UserEntry.TABLE_NAME,
                projection2,
                selection2,
                selectionArgs2,
                null,
                null,
                null
        );

        cursor2.moveToFirst();
        int coins = cursor2.getInt(cursor2.getColumnIndexOrThrow(UserInfo.UserEntry.COINS));
        cursor2.close();
        coinsText.setText(String.format("Total Coins: %d", coins));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
