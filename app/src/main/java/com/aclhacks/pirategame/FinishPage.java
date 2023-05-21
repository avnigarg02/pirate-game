package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.*;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;


public class FinishPage extends AppCompatActivity {

    private static final int REQUEST_USAGE_STATS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yay);

        if (!hasUsageStatsPermission()) {
            requestUsageStatsPermission();
        } else {
            // Permission already granted, proceed with retrieving usage stats
            retrieveUsageStats();
        }

    }

    // request permission to collect screen time stats
    private boolean hasUsageStatsPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private void requestUsageStatsPermission() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
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
                noPermission();
            }
        }
    }

    private void retrieveUsageStats() {
        // Code to retrieve usage stats here
//        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
//
//        // Set the desired time range for which you want to retrieve usage stats
//        long startTime = // Specify start time in milliseconds
//        long endTime = // Specify end time in milliseconds
//        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
//
//        // Process the retrieved usage stats
//        for (UsageStats usageStats : usageStatsList) {
//            String packageName = usageStats.getPackageName();
//            long totalUsageTime = usageStats.getTotalTimeInForeground();
//
//            // Process the package name and usage time as needed
//            // ...
//        }

    }

    private void noPermission() {
        // no
    }

}
