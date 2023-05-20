package com.aclhacks.pirategame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDataDbHelper extends SQLiteOpenHelper
{
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserInfo.UserEntry.TABLE_NAME + " (" +
                    UserInfo.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserInfo.UserEntry.FULL_NAME + " TEXT," +
                    UserInfo.UserEntry.EMAIL + " TEXT," +
                    UserInfo.UserEntry.ALLOWED_APPS + " TEXT," +
                    UserInfo.UserEntry.BLOCKED_APPS + " TEXT," +
                    UserInfo.UserEntry.USAGE_HISTORY + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserInfo.UserEntry.TABLE_NAME;

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
