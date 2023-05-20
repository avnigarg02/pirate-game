package com.aclhacks.pirategame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.preference.PreferenceManager;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import android.Manifest;

import java.util.ArrayList;
import android.content.Context;

import com.caverock.androidsvg.BuildConfig;

import org.osmdroid.config.Configuration;




public class Path extends AppCompatActivity {

    private static final int REQUEST_USAGE_STATS = 1;
    private static final int REQUEST_MAPS = 1;
    private MapView map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.map_view);


        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

//        IMapController mapController = map.getController();
//        mapController.setZoom(9.5);
//        GeoPoint startPoint = new GeoPoint(38.8977, -77.0365);
//        mapController.setCenter(startPoint);

        if (!hasUsageStatsPermission())
        {
            requestUsageStatsPermission();
        }
        else
        {
            // Permission already granted, proceed with retrieving usage stats
            retrieveUsageStats();
        }

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
