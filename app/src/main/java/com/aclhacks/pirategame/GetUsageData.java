package com.aclhacks.pirategame;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.AppOpsManager;
import android.app.usage.*;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.List;

public class GetUsageData {

//    public float total_time(long start, long end) {
//        final List<UsageStats> queryUsageStats = UsageStatsManager.queryUsageStats(start, end);
//    }

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

//    AppOpsManager.OPSTR_GET_USAGE_STATS = "android:get_usage_stats";



}
