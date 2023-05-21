package com.aclhacks.pirategame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.app.usage.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.preference.PreferenceManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import android.Manifest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;


public class PathPage extends AppCompatActivity implements BoatOverlay.OverlayListener {

    private static final int REQUEST_MAPS = 1;
    private MapView map = null;
    private BoatOverlay boat;
    private static final int REQUEST_USAGE_STATS = 1;
    private Handler handler;
    private Runnable runnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.map_view);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!hasUsageStatsPermission()) {
                    requestUsageStatsPermission();
                } else {
                    // Permission already granted, proceed with retrieving usage stats
                    retrieveUsageStats();
                }
                handler.postDelayed(this, 1000 * 60);
            }
        };
        handler.post(runnable);


        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        AssetManager assetManager = getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open("Coords.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner coords = new Scanner(inputStream, "UTF-8");
        List<List<Double>> points = new ArrayList<>();
        while (coords.hasNextLine())
        {
            String line = coords.nextLine();
            String[] lineArr = line.split(",");
            List<Double> point = new ArrayList<>();
            point.add(Double.parseDouble(lineArr[0]));
            point.add(Double.parseDouble(lineArr[1]));
            points.add(point);
        }
        List<Double> randPoint = points.get((int) (Math.random() * points.size()));

        IMapController mapController = map.getController();
        mapController.setZoom(10);
        Marker startMarker = new Marker(map);
        GeoPoint startPoint = new GeoPoint(randPoint.get(0), randPoint.get(1));
        mapController.setCenter(startPoint);
        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);
        Drawable boatDrawable = ContextCompat.getDrawable(this, R.drawable.boat);
        int minutes = getIntent().getIntExtra("time", 0);
        System.out.println(minutes);
        boat = new BoatOverlay(boatDrawable, startPoint, 2000, minutes, map);
        boat.setOverlayListener(this);
        map.getOverlays().add(boat);
        map.invalidate();



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

    @Override
    public void onSignalReceived() {
        boat = null;
        Intent intent = new Intent(this, FinishPage.class);
        intent.putExtra("coins", getIntent().getIntExtra("coins", 0));
        intent.putExtra("start", getIntent().getLongExtra("start", 0));
        intent.putExtra("userId", getIntent().getLongExtra("userId", 0));
        startActivity(intent);
        finish();
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
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        // Set the desired time range for which you want to retrieve usage stats
//        long startTime = getIntent().getLongExtra("start", 0);
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 1000 * 60; // 1 minute
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        // Process the retrieved usage stats
        boolean passing = true;
        for (UsageStats usageStats : usageStatsList) {
            String packageName = usageStats.getPackageName();
//            long totalUsageTime = usageStats.getTotalTimeInForeground();
            long lastTimeUsed = usageStats.getLastTimeUsed();

            // Process the package name and usage time as needed
            if (!packageName.startsWith("com.android.") && !packageName.equals("com.aclhacks.pirategame")) {
                if (lastTimeUsed >= startTime) {
                    passing = false;
                    break;
                }
            }
        }

        // shipwreck if fail - use apps for more than a second in past minute
//        if (!passing) {
//            boat = null;
//            Intent intent = new Intent(PathPage.this, ShipwreckedPage.class);
//            startActivity(intent);
//            finish();
//        }

    }

    private void noPermission() {
        // no
    }
}
