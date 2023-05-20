package com.aclhacks.pirategame;
import android.content.Context;
import android.database.sqlite.*;
import android.provider.BaseColumns;

public final class LoginInfo {

    private LoginInfo(){}

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.USERNAME + " TEXT," +
                    UserEntry.PASSWORD + " TEXT," +
                    UserEntry.USER_ID + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;


    public static class UserEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "login_info";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String USER_ID = "user_id";

    }

    public class UserDataDbHelper extends SQLiteOpenHelper
    {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "LoginInfo.db";

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
}
