package com.aclhacks.pirategame;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class UserInfo
{
    private UserInfo(){}

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.FULL_NAME + " TEXT," +
                    UserEntry.EMAIL + " TEXT," +
                    UserEntry.ALLOWED_APPS + " TEXT," +
                    UserEntry.BLOCKED_APPS + " TEXT," +
                    UserEntry.USAGE_HISTORY + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    public static class UserEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "user_info";
        public static final String FULL_NAME = "full_name";
        public static final String EMAIL = "email";
        public static final String ALLOWED_APPS = "allowed_apps";
        public static final String BLOCKED_APPS = "blocked_apps";
        public static final String USAGE_HISTORY = "usage_history";
    }

    public class UserDataDbHelper extends SQLiteOpenHelper
    {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "UserInfo.db";

        public UserDataDbHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL(SQL_DELETE_ENTRIES);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

//    public static void CreateUser(String username, String password, String fullName, String email)
//    {
//        //create instance of user info db helper
//        UserInfo.UserDataDbHelper mDbHelper = new UserInfo.UserDataDbHelper(getContext());
//    }


}
