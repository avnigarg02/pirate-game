package com.aclhacks.pirategame;
import android.content.Context;
import android.database.sqlite.*;
import android.provider.BaseColumns;

public final class LoginInfo {

    private LoginInfo(){}

    public static class UserEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "login_info";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String USER_ID = "user_id";

    }
}
