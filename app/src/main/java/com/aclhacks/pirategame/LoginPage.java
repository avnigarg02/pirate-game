package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class LoginPage extends AppCompatActivity
{
    // Declare your variables and views here
    private Button signupButton;
    private Button loginButton;
    private EditText password;

    private EditText username;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        signupButton = findViewById(R.id.signUpBtn);
        loginButton = findViewById(R.id.loginbtn);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //set content view to map
                if (login(username.getText().toString(), password.getText().toString()) != null)
                {
                    Intent intent = new Intent(LoginPage.this, ChoicePage.class);
                    System.out.println(userId);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //set content view to signup page
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // account information

    public SQLiteDatabase login(String username, String password)
    {
        LoginDataDbHelper mDbHelper = new LoginDataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                LoginInfo.UserEntry.USER_ID,
                LoginInfo.UserEntry.USERNAME,
                LoginInfo.UserEntry.PASSWORD
        };

        String selection = LoginInfo.UserEntry.USERNAME + " = ? AND " + LoginInfo.UserEntry.PASSWORD + " = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(
                LoginInfo.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() == 0)
        {
            return null;
        }
        else
        {
            cursor.moveToFirst();
            userId = cursor.getLong(cursor.getColumnIndexOrThrow(LoginInfo.UserEntry.USER_ID));
            cursor.close();
            //access user data with userId
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
            String fullName = cursor2.getString(cursor2.getColumnIndexOrThrow(UserInfo.UserEntry.FULL_NAME));
            String email = cursor2.getString(cursor2.getColumnIndexOrThrow(UserInfo.UserEntry.EMAIL));
            String allowedApps = cursor2.getString(cursor2.getColumnIndexOrThrow(UserInfo.UserEntry.ALLOWED_APPS));
            String blockedApps = cursor2.getString(cursor2.getColumnIndexOrThrow(UserInfo.UserEntry.BLOCKED_APPS));
            String usageHistory = cursor2.getString(cursor2.getColumnIndexOrThrow(UserInfo.UserEntry.USAGE_HISTORY));
            cursor2.close();
            return db2;
        }
    }


}