package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;


import android.Manifest;
import android.content.Context;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    // Declare your variables and views here
    private Button signupButton;
    private EditText email;
    private EditText password;
    private EditText passwordConfirm;
    private EditText fullName;
    private EditText username;

    private static final int REQUEST_USAGE_STATS = 1;
    private final int REQUEST_MAPS = 1;
    private MapView map = null;

    @SuppressLint({"ResourceType", "MissingInflatedId"})
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
                if (password.getText().toString().equals(passwordConfirm.getText().toString()))
                {
                    createAccount(username.getText().toString(), password.getText().toString(), email.getText().toString(), fullName.getText().toString());
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    private void showMap()
    {
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.map_view);


        map = (MapView) findViewById(R.layout.map_view);
        map.setTileSource(TileSourceFactory.MAPNIK);

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
    }
    // request permission to collect screen time stats
    private boolean hasUsageStatsPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private void requestUsageStatsPermission() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivityForResult(intent, REQUEST_USAGE_STATS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_USAGE_STATS) {
            if (hasUsageStatsPermission()) {
                // Permission granted, proceed with retrieving usage stats
                retrieveUsageStats();
            } else {
                // Permission denied by the user
                noPermsision();
            }
        }
    }

    private void retrieveUsageStats() {
        // Code to retrieve usage stats here
        // ...
    }

    private void noPermsision() {
        // no
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

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_MAPS);
        }
    }

    private static boolean validateEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}