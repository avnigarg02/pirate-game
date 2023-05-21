package com.aclhacks.pirategame;

import org.osmdroid.views.overlay.Overlay;
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

    public BoatOverlay(Drawable boatDrawable, GeoPoint startPoint, double distance, int minutes, MapView map) {
        this.boatDrawable = boatDrawable;
        this.startPoint = startPoint;
        this.distance = distance;
        this.mapView = map;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateBoatPosition();
                mapView.invalidate();
                handler.postDelayed(this, 600 * minutes); // 60 FPS (adjust as needed)
            }
        };
        handler.post(runnable);
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

        drawPath(canvas, startPoint, endPoint);
    }

    private void drawPath(Canvas canvas, GeoPoint startPoint, GeoPoint endPoint) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        int numPoints = 100; // Adjust the number of points for a smoother curve

        double distance = startPoint.distanceToAsDouble(endPoint);
        double amplitude = distance / 2.0; // Set the desired amplitude of the wave
        double frequency = 2.0 * Math.PI / distance; // Set the desired frequency of the wave

        double deltaX = endPoint.getLongitude() - startPoint.getLongitude();
        double deltaY = endPoint.getLatitude() - startPoint.getLatitude();

        for (int i = 0; i <= numPoints; i++) {
            double progress = (double) i / numPoints;
            double waveOffset = Math.sin(i * frequency); // Incorporate the 'i' value into the wave calculation

            double currentDeltaX = deltaX * Math.cos(progress * frequency) * amplitude * waveOffset;
            double currentDeltaY = deltaY * Math.sin(progress * frequency) * amplitude * waveOffset;

            double currentLon = startPoint.getLongitude() + currentDeltaX;
            double currentLat = startPoint.getLatitude() + currentDeltaY;
            GeoPoint point = new GeoPoint(currentLat, currentLon);

            Point screenPoint = new Point();
            mapView.getProjection().toPixels(point, screenPoint);

            if (i == 0) {
                path.moveTo(screenPoint.x, screenPoint.y);
            } else {
                path.lineTo(screenPoint.x, screenPoint.y);
            }
        }

        canvas.drawPath(path, paint);
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
        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(distanceInMeters / earthRadius) +
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
        double distance = startPoint.distanceToAsDouble(endPoint);
        double amplitude = distance / 2.0; // Set the desired amplitude of the wave
        double frequency = 2.0 * Math.PI / distance; // Set the desired frequency of the wave

        double deltaX = endPoint.getLongitude() - startPoint.getLongitude();
        double deltaY = endPoint.getLatitude() - startPoint.getLatitude();
        double currentDeltaX = deltaX * Math.cos(progress * frequency);
        double currentDeltaY = deltaY * Math.sin(progress * frequency);

        double currentLon = startPoint.getLongitude() + currentDeltaX;
        double currentLat = startPoint.getLatitude() + currentDeltaY;

        return new GeoPoint(currentLat, currentLon);
    }

    private void updateBoatPosition() {
        if (progress < 1) {
            progress += 0.01; // Adjust the increment as needed for the desired boat speed
        } else {
            progress = 1;
        }
    }
}
