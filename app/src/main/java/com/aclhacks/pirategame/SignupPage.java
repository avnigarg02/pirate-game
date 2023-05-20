package com.aclhacks.pirategame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignupPage extends AppCompatActivity
{
    private EditText passwordConfirm;
    private EditText fullName;
    private EditText email;
    private EditText password;
    private EditText username;
    private Button signupButton;

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
            public void onClick(View view)
            {
                //set content view to map
                if (emailIsValid(email.getText().toString()))
                {
                    createAccount(username.getText().toString(), password.getText().toString(), email.getText().toString(), fullName.getText().toString());
                    Intent intent = new Intent(SignupPage.this, LoginPage.class);
                    startActivity(intent);
                }
            }
        });
    }

    private static boolean emailIsValid(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
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



}
