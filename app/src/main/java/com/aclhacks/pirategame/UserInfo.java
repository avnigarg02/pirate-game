package com.aclhacks.pirategame;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class UserInfo
{
    private UserInfo(){}

    public static class UserEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "user_info";
        public static final String FULL_NAME = "full_name";
        public static final String EMAIL = "email";
        public static final String ALLOWED_APPS = "allowed_apps";
        public static final String BLOCKED_APPS = "blocked_apps";
        public static final String USAGE_HISTORY = "usage_history";
        public static final String COINS = "coins";
    }


}
