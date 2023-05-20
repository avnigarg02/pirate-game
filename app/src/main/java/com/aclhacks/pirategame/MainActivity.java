package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;


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
        setContentView(R.layout.login);
        signupButton = findViewById(R.id.signUpBtn);
//        email = findViewById(R.id.emailSignUp);
        password = findViewById(R.id.password);
//        passwordConfirm = findViewById(R.id.passwordConfirmation);
//        fullName = findViewById(R.id.fullname);
//        username = findViewById(R.id.username);
        signupButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //set content view to map
                if (login(username.getText().toString(), password.getText().toString()) != null)
                {
                    setContentView(R.layout.map_view);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // account information
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
            long userId = cursor.getLong(cursor.getColumnIndexOrThrow(LoginInfo.UserEntry.USER_ID));
            cursor.close();
            //access user data with userId
            UserDataDbHelper mDbHelper2 = new UserDataDbHelper(this);
            SQLiteDatabase db2 = mDbHelper2.getReadableDatabase();

            String[] projection2 = {
                    UserInfo.UserEntry.FULL_NAME,
                    UserInfo.UserEntry.EMAIL,
                    UserInfo.UserEntry.ALLOWED_APPS,
                    UserInfo.UserEntry.BLOCKED_APPS,
                    UserInfo.UserEntry.USAGE_HISTORY
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


    private static boolean validateEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}