package com.aclhacks.pirategame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LeaderboardPage extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
    }

    public List<String> getLeaderboard()
    {
        return null;
    }
}
