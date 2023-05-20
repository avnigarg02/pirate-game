package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    // Declare your variables and views here

    private Button signupButton;
    private EditText email;
    private EditText password;
    private EditText passwordConfirm;
    private EditText fullName;
    private EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        signupButton = findViewById(R.id.signUpBtn);
        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirmation);
        fullName = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (password.getText().toString().equals(passwordConfirm.getText().toString()))
                {
                    createAccount(username.getText().toString(), password.getText().toString(), email.getText().toString(), fullName.getText().toString());
                }
            }
        });
    }


    public void createAccount(String username, String password, String email, String fullName)
    {
        UserDataDbHelper mDbHelper = new UserDataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserInfo.UserEntry.FULL_NAME, fullName);
        values.put(UserInfo.UserEntry.EMAIL, email);
        values.put(UserInfo.UserEntry.ALLOWED_APPS, "");
        values.put(UserInfo.UserEntry.BLOCKED_APPS, "");
        values.put(UserInfo.UserEntry.USAGE_HISTORY, "");

        long newRowId = db.insert(UserInfo.UserEntry.TABLE_NAME, null, values);
        LoginDataDbHelper mDbHelper2 = new LoginDataDbHelper(this);
        SQLiteDatabase db2 = mDbHelper2.getWritableDatabase();

        ContentValues values2 = new ContentValues();
        values2.put(LoginInfo.UserEntry.USERNAME, username);
        values2.put(LoginInfo.UserEntry.PASSWORD, password);
        values2.put(LoginInfo.UserEntry.USER_ID, newRowId);

        long newRowId2 = db2.insert(LoginInfo.UserEntry.TABLE_NAME, null, values2);
    }

    private boolean validateEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



//    private void requestUsageStatsPermission() {
//        if (!hasUsageStatsPermission()) {
//            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//            startActivity(intent);
//        }
//    }
//
//    private boolean hasUsageStatsPermission() {
//        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), getPackageName());
//        return mode == AppOpsManager.MODE_ALLOWED;
//    }

    // Create additional methods for various app functionalities
}