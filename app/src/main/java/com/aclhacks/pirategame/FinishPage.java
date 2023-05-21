package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.*;


public class FinishPage extends AppCompatActivity {

    private Button home;
    private TextView coinsText;
    private ImageButton settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yay);

        home = findViewById(R.id.home);
        coinsText = findViewById(R.id.textView7);
        settings = findViewById(R.id.settings);

        int coins = getIntent().getIntExtra("coins", 0);
        coinsText.setText(String.format("+%d coins", coins));

        long userId = getIntent().getLongExtra("userId", 0);

        //read current amount of coins
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
        int currCoins = cursor2.getInt(cursor2.getColumnIndexOrThrow(UserInfo.UserEntry.COINS));
        cursor2.close();

        //add coins to current amount of coins
        UserDataDbHelper mDbHelper = new UserDataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserInfo.UserEntry.COINS, coins + currCoins);
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

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishPage.this, SettingsPage.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

    }

}
