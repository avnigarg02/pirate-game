package com.aclhacks.pirategame;

import org.osmdroid.views.overlay.Overlay;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

public class BoatOverlay extends Overlay {
    private Polyline path;
    private Drawable boatDrawable;
    private GeoPoint startPoint;
    private double distance;
    private double progress = 0;
    private Handler handler;
    private Runnable runnable;

    private MapView mapView;
    private OverlayListener overlayListener;

    private double speed;


    public BoatOverlay(Drawable boatDrawable, GeoPoint startPoint, double distance, int minutes, MapView map) {
        this.boatDrawable = boatDrawable;
        this.startPoint = startPoint;
        this.distance = distance;
        this.mapView = map;
        this.speed = 0.01;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(!updateBoatPosition())
                {
                    if (overlayListener != null)
                    {
                        overlayListener.onSignalReceived();
                    }

                }
                else
                {
                    mapView.invalidate();
                    handler.postDelayed(this, 600 * minutes); // 60 FPS (adjust as needed)
                }
            }
        };
        handler.post(runnable);
    }

    public interface OverlayListener {
        void onSignalReceived();
    }

    public void setOverlayListener(OverlayListener listener) {
        this.overlayListener = listener;
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if (shadow) {
            return;
        }

        GeoPoint endPoint = calculateEndPoint(startPoint, distance);
        GeoPoint boatPosition = calculateBoatPosition(startPoint, endPoint, progress);

        if (boatPosition != null) {
            Point screenPosition = new Point();
            mapView.getProjection().toPixels(boatPosition, screenPosition);
            int x = screenPosition.x - boatDrawable.getIntrinsicWidth() / 2;
            int y = screenPosition.y - boatDrawable.getIntrinsicHeight() / 2;
            boatDrawable.setBounds(x, y, x + boatDrawable.getIntrinsicWidth(), y + boatDrawable.getIntrinsicHeight());
            boatDrawable.draw(canvas);
        }

        drawPath(canvas, startPoint, boatPosition);
    }

    private void drawPath(Canvas canvas, GeoPoint startPoint, GeoPoint endPoint) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo((float) startPoint.getLongitude(), (float) startPoint.getLatitude());
        path.lineTo((float) endPoint.getLongitude(), (float) endPoint.getLatitude());

        Point sP = mapView.getProjection().toPixels(startPoint, null);
        Point eP = mapView.getProjection().toPixels(endPoint, null);

        canvas.drawLine(sP.x, sP.y, eP.x, eP.y, paint);
    }



    private GeoPoint calculateEndPoint(GeoPoint startPoint, double distance) {
        // Convert distance to meters (if needed)
        double distanceInMeters = distance;

        // Earth's radius in meters
        double earthRadius = 6371000;

        // Convert latitude and longitude to radians
        double lat1 = Math.toRadians(startPoint.getLatitude());
        double lon1 = Math.toRadians(startPoint.getLongitude());

        // Calculate the end point's latitude
        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(distanceInMeters / earthRadius) -
                Math.cos(lat1) * Math.sin(distanceInMeters / earthRadius) * Math.cos(0));

        // Calculate the end point's longitude
        double lon2 = lon1 + Math.atan2(Math.sin(0) * Math.sin(distanceInMeters / earthRadius) * Math.cos(lat1),
                Math.cos(distanceInMeters / earthRadius) - Math.sin(lat1) * Math.sin(lat2));

        // Convert back to degrees
        double endLatitude = Math.toDegrees(lat2);
        double endLongitude = Math.toDegrees(lon2);

        return new GeoPoint(endLatitude, endLongitude);
    }

    private GeoPoint calculateBoatPosition(GeoPoint startPoint, GeoPoint endPoint, double progress) {
        double currentLat = endPoint.getLatitude() - (endPoint.getLatitude() - startPoint.getLatitude()) * progress;
        double currentLon = endPoint.getLongitude() - (endPoint.getLongitude() - startPoint.getLongitude()) * progress;

        return new GeoPoint(currentLat, currentLon);
    }

    private boolean updateBoatPosition() {
        if (progress < 1) {
            progress += speed; // Adjust the increment as needed for the desired boat speed
            return true;
        } else {
            progress = 1;
            return false;
        }
    }
}
