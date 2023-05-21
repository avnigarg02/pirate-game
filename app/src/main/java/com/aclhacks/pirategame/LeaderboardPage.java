package com.aclhacks.pirategame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardPage extends AppCompatActivity
{
    private Button back;
    private TextView first;
    private TextView second;
    private TextView third;
    private TextView fourth;
    private TextView fifth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        List<String> leaderboard = getLeaderboard();
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        fifth = findViewById(R.id.fifth);

        if (leaderboard.size() > 0) {
            first.setText(leaderboard.get(0));
        }
        if (leaderboard.size() > 1) {
            second.setText(leaderboard.get(1));
        }
        if (leaderboard.size() > 2) {
            third.setText(leaderboard.get(2));
        }
        if (leaderboard.size() > 3) {
            fourth.setText(leaderboard.get(3));
        }
        if (leaderboard.size() > 4) {
            fifth.setText(leaderboard.get(4));
        }

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    public List<String> getLeaderboard()
    {
        UserDataDbHelper mDbHelper2 = new UserDataDbHelper(this);
        SQLiteDatabase db2 = mDbHelper2.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT * FROM " + UserInfo.UserEntry.TABLE_NAME + " ORDER BY " + UserInfo.UserEntry.COINS + " DESC", null);

        List<String> leaderboard = new ArrayList<>();
        int i = 1;
        if (cursor.moveToFirst()) {
            // Iterate over the rows (users)
            do {
                // Access the user data here
                @SuppressLint("Range" ) String fullName = cursor.getString(cursor.getColumnIndex(UserInfo.UserEntry.FULL_NAME));
                @SuppressLint("Range" ) int coins = cursor.getInt(cursor.getColumnIndex(UserInfo.UserEntry.COINS));
                leaderboard.add(i + " - " + fullName + ": " + coins);

                // ... Access other columns as needed

                // Process the user data or perform any required operations
                i++;
            } while (cursor.moveToNext()); // Move to the next row if available
        }

// Close the cursor when you're done with it
        cursor.close();
        return leaderboard;
    }
}
